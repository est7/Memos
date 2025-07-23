package app.xat.memos

import app.xat.memos.core.PageState
import app.xat.memos.core.PaginationDelegate
import app.xat.memos.core.PaginationStateProvider

/**
 * UI state for products screen
 *
 * @author: est8
 * @date: 7/23/25
 */
data class ProductsState(
    val pagination: PageState<ProductDto> = PageState.Loading,
    val selectedCategory: String? = null,
    val sortOrder: SortOrder = SortOrder.DEFAULT
) : PaginationStateProvider by PaginationDelegate(pagination) {
    val products: List<ProductDto>
        get() = when (pagination) {
            is PageState.Success -> pagination.items
            else -> emptyList()
        }
}