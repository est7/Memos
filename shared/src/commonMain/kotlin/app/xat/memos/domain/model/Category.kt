package app.xat.memos.domain.model

/**
 * Domain model for category
 *
 * @author: est8
 * @date: 7/25/25
 */
data class Category(
    val id: Long = 0,
    val value: String,
    val label: String,
    val createdAt: String = "",
    val updatedAt: String = ""
)