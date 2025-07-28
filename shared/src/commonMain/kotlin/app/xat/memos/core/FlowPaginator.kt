import app.xat.memos.core.PageState
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock

/**
 * 通用 Flow 分页器
 *
 * @param initialKey     第一页 key
 * @param scope          作用域（一般 viewModelScope）
 * @param request        suspend 请求函数，返回 List<Item>
 * @param getNextKey     根据当前 key 和返回数据计算下一页 key，若无更多返回 null
 * @param onReachEnd     判断是否到底。默认当 nextKey == null
 */
class FlowPaginator<Key : Any, Item : Any>(
    private val initialKey: Key,
    private val scope: CoroutineScope,
    private val request: suspend (Key) -> Result<List<Item>>,
    private val getNextKey: (Key, List<Item>) -> Key?,
    private val onReachEnd: (Key?, List<Item>) -> Boolean = { _, list -> list.isEmpty() }
) {

    /* ========== 对外 State ========== */
    private val _state = MutableStateFlow<PageState<Item>>(PageState.Loading)
    val state: StateFlow<PageState<Item>> = _state.asStateFlow()

    /* ========== 快捷方法 ========== */
    fun refreshAsync() = _actions.tryEmit(Action.Refresh)
    fun loadMoreAsync() = _actions.tryEmit(Action.LoadMore)
    fun retryAsync() = _actions.tryEmit(Action.Retry)

    suspend fun refresh() = _actions.emit(Action.Refresh)
    suspend fun loadMore() = _actions.emit(Action.LoadMore)
    suspend fun retry() = _actions.emit(Action.Retry)

    fun cancel() = job?.cancel()

    /* ========== 内部实现 ========== */
    private sealed interface Action {
        object Refresh : Action
        object LoadMore : Action;
        object Retry : Action
    }

    private val _actions = MutableSharedFlow<Action>(extraBufferCapacity = 32)

    private var job: Job? = null
    private var key: Key? = initialKey
    private val mutex = Mutex()

    init {
        _actions.onEach(::handleAction).launchIn(scope)
    }

    private suspend fun handleAction(action: Action) = mutex.withLock {
        when (action) {
            Action.Refresh -> onRefresh()
            Action.LoadMore -> onLoadMore()
            Action.Retry -> onRetry()
        }
    }

    /* ----------- 刷新 ----------- */
    private fun onRefresh() {
        if (_state.value is PageState.Success.Refreshing) return
        key = initialKey
        launchRequest(isRefresh = true)
    }

    /* ----------- 加载更多 ----------- */
    private fun onLoadMore() {
        val s = _state.value as? PageState.Success<Item> ?: return
        if (s is PageState.Success.EndReached ||
            s is PageState.Success.LoadingMore
        ) return
        if (key == null) return                                 // 已无下一页
        launchRequest(isRefresh = false)
    }

    /* ----------- 重试 ----------- */
    private fun onRetry() {
        when (val s = _state.value) {
            is PageState.Error -> launchRequest(isRefresh = true)
            is PageState.Success.LoadMoreFail ->
                launchRequest(isRefresh = false)

            else -> Unit
        }
    }

    /* ----------- 网络请求 ----------- */
    private fun launchRequest(isRefresh: Boolean) {
        val useKey = key ?: return
        job?.cancel()
        job = scope.launch {
            emitLoadingState(isRefresh)
            val result = runCatching { request(useKey) }
                .getOrElse { Result.failure(it) }

            result.fold(
                onSuccess = { list -> onSuccess(list, isRefresh, useKey) },
                onFailure = { t -> onError(t, isRefresh) }
            )
        }
    }

    private suspend fun emitLoadingState(isRefresh: Boolean) {
        _state.emit(
            when {
                isRefresh && _state.value is PageState.Success ->
                    PageState.Success.Refreshing((_state.value as PageState.Success<Item>).items)

                isRefresh -> PageState.Loading
                else -> {
                    val cur = _state.value as? PageState.Success<Item> ?: return
                    PageState.Success.LoadingMore(cur.items)
                }
            }
        )
    }

    private suspend fun onSuccess(list: List<Item>, isRefresh: Boolean, usedKey: Key) {
        val next = getNextKey(usedKey, list)
        key = next
        val end = onReachEnd(next, list)

        val merged = if (isRefresh) list
        else ((_state.value as? PageState.Success<Item>)?.items ?: emptyList()) + list

        _state.emit(
            when {
                merged.isEmpty() -> PageState.Empty
                end -> PageState.Success.EndReached(merged)
                isRefresh -> PageState.Success.Idle(merged)
                else -> PageState.Success.Idle(merged)
            }
        )
    }

    private suspend fun onError(t: Throwable, isRefresh: Boolean) {
        if (isRefresh) {
            _state.emit(PageState.Error(t.message ?: "Unknown Error"))
        } else {
            val cur = _state.value as? PageState.Success<Item> ?: return
            _state.emit(PageState.Success.LoadMoreFail(cur.items))
        }
    }
}
