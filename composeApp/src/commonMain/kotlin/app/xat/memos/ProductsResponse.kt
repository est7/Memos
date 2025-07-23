package app.xat.memos

/**
 * API response wrapper for products
 *
 * @author: est8
 * @date: 7/23/25
 */
data class ProductsResponse(
    val products: List<ProductDto>,
    val totalCount: Int,
    val hasMore: Boolean
)