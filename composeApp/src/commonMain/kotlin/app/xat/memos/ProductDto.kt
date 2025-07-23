package app.xat.memos

/**
 * Product data transfer object
 *
 * @author: est8
 * @date: 7/23/25
 */
data class ProductDto(
    val id: Long,
    val name: String,
    val description: String,
    val price: Double,
    val imageUrl: String? = null,
    val category: String,
    val inStock: Boolean = true,
    val createdAt: String
)