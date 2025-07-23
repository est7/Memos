package app.xat.memos

import kotlinx.coroutines.delay

/**
 * Mock implementation of ProductsApi for testing pagination
 *
 * @author: est8
 * @date: 7/23/25
 */
class MockProductsApi : ProductsApi {
    
    private val allProducts = generateMockProducts()
    
    override suspend fun getProducts(
        page: Int,
        pageSize: Int,
        category: String?,
        sortOrder: SortOrder
    ): ProductsResponse {
        // Simulate network delay
        delay(1000)
        
        val startIndex = (page - 1) * pageSize
        val endIndex = minOf(startIndex + pageSize, allProducts.size)
        
        val filteredProducts = if (category != null) {
            allProducts.filter { it.category == category }
        } else {
            allProducts
        }
        
        val sortedProducts = when (sortOrder) {
            SortOrder.NAME_ASC -> filteredProducts.sortedBy { it.name }
            SortOrder.NAME_DESC -> filteredProducts.sortedByDescending { it.name }
            SortOrder.PRICE_ASC -> filteredProducts.sortedBy { it.price }
            SortOrder.PRICE_DESC -> filteredProducts.sortedByDescending { it.price }
            SortOrder.CREATED_ASC -> filteredProducts.sortedBy { it.createdAt }
            SortOrder.CREATED_DESC -> filteredProducts.sortedByDescending { it.createdAt }
            SortOrder.DEFAULT -> filteredProducts
        }
        
        val pageProducts = if (startIndex < sortedProducts.size) {
            sortedProducts.subList(startIndex, minOf(endIndex, sortedProducts.size))
        } else {
            emptyList()
        }
        
        return ProductsResponse(
            products = pageProducts,
            totalCount = sortedProducts.size,
            hasMore = endIndex < sortedProducts.size
        )
    }
    
    private fun generateMockProducts(): List<ProductDto> {
        val categories = listOf("Electronics", "Clothing", "Books", "Home", "Sports")
        val products = mutableListOf<ProductDto>()
        
        repeat(100) { index ->
            products.add(
                ProductDto(
                    id = index.toLong() + 1,
                    name = "Product ${index + 1}",
                    description = "This is a detailed description for product ${index + 1}. It contains all the important information about this amazing product.",
                    price = (10.0 + (index * 5.5)) % 500.0,
                    imageUrl = "https://example.com/product${index + 1}.jpg",
                    category = categories[index % categories.size],
                    inStock = index % 7 != 0, // Some products out of stock
                    createdAt = "2025-07-${(index % 30) + 1}"
                )
            )
        }
        
        return products
    }
}