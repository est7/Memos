package app.xat.memos.domain.model

/**
 * Domain model for payment method
 *
 * @author: est8
 * @date: 7/25/25
 */
data class PaymentMethod(
    val id: Long = 0,
    val value: String,
    val label: String,
    val createdAt: String = "",
    val updatedAt: String = ""
)