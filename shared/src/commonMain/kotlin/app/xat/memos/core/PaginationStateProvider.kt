package app.xat.memos.core

/**
 *  创建一个接口来定义分页状态的便捷属性*
 * @author: est8
 * @date: 7/23/25
 */
interface PaginationStateProvider {
    val isInitialLoading: Boolean
    val isContentRefreshing: Boolean
    val isLoadingMore: Boolean
    val canLoadMore: Boolean
    val hasError: Boolean
    val isLoadMoreError: Boolean
    val isEmpty: Boolean
    val isEndReached: Boolean
}

