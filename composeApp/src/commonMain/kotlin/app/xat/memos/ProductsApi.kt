package app.xat.memos

/**
 * API interface for products operations
 *
 * @author: est8
 * @date: 7/23/25
 */
interface ProductsApi {
    
    /**
     * Get products with pagination
     * @param page Page number (starting from 1)
     * @param pageSize Number of items per page (optional, defaults to PAGE_SIZE)
     * @param category Filter by category (optional)
     * @param sortOrder Sort order (optional, defaults to DEFAULT)
     * @return ProductsResponse containing products and pagination info
     */
    suspend fun getProducts(
        page: Int,
        pageSize: Int = PAGE_SIZE,
        category: String? = null,
        sortOrder: SortOrder = SortOrder.DEFAULT
    ): ProductsResponse
}