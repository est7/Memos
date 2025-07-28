package app.xat.memos.core

// 创建一个委托类
class PaginationDelegate<T>(private val pageState: PageState<T>) : PaginationStateProvider {
    override val isInitialLoading: Boolean get() = pageState.isInitialLoading
    override val isContentRefreshing: Boolean get() = pageState.isContentRefreshing
    override val isLoadingMore: Boolean get() = pageState.isLoadingMore
    override val canLoadMore: Boolean get() = pageState.canLoadMore
    override val hasError: Boolean get() = pageState.hasError
    override val isLoadMoreError: Boolean get() = pageState.isLoadMoreError
    override val isEmpty: Boolean get() = pageState.isEmpty
    override val isEndReached: Boolean get() = pageState.isEndReached
}