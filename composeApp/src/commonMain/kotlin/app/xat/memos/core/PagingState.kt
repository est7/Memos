package app.xat.memos.core

sealed class PageState<out T>:PaginationStateProvider {
    object Loading : PageState<Nothing>()
    object Empty : PageState<Nothing>()
    data class Error(val msg: String) : PageState<Nothing>()
    sealed class Success<T> : PageState<T>() {
        abstract val items: List<T>

        data class Idle<T>(override val items: List<T>) : Success<T>()
        data class Refreshing<T>(override val items: List<T>) : Success<T>()
        data class LoadingMore<T>(override val items: List<T>) : Success<T>()
        data class LoadMoreFail<T>(override val items: List<T>) : Success<T>()
        data class EndReached<T>(override val items: List<T>) : Success<T>()
    }

    // 初始化时无数据刷新
    override val isInitialLoading: Boolean
        get() = this is PageState.Loading

    // 此时的刷新是有内容的刷新
    override val isContentRefreshing: Boolean
        get() = this is PageState.Success.Refreshing

    override val isLoadingMore: Boolean
        get() = this is PageState.Success.LoadingMore

    override val canLoadMore: Boolean
        get() = this is PageState.Success.Idle || this is PageState.Success.LoadMoreFail

    override val hasError: Boolean
        get() = this is PageState.Error

    override val isLoadMoreError: Boolean
        get() = this is PageState.Success.LoadMoreFail

    override val isEmpty: Boolean
        get() = this is PageState.Empty

    override val isEndReached: Boolean
        get() = this is PageState.Success.EndReached

}