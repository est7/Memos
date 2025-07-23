package app.xat.memos

import FlowPaginator
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn

/**
 *
 * @author: est8
 * @date: 7/23/25
 */
class ProductsViewModel(
    private val api: ProductsApi
) : ViewModel() {

    private val paginator = FlowPaginator(
        initialKey = 1,
        scope = viewModelScope,
        request = { page -> runCatching { api.getProducts(page).products } },
        getNextKey = { page, _ -> page + 1 },
        onReachEnd = { _, list -> list.size < PAGE_SIZE }
    )

    val state: StateFlow<ProductsState> = paginator.state.map { pageState ->
        ProductsState(pagination = pageState)
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = ProductsState()
    )

    init {
        refresh()
    }
    fun refresh() = paginator.refreshAsync()
    fun loadMore() = paginator.loadMoreAsync()
    fun retry() = paginator.retryAsync()
}