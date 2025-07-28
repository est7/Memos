package app.xat.memos.domain.model

/**
 * Domain model for subscription
 *
 * @author: est8
 * @date: 7/25/25
 */
data class Subscription(
    val id: Long = 0,
    val name: String,
    val plan: String,
    val billingCycle: BillingCycle,
    val nextBillingDate: String?,
    val lastBillingDate: String?,
    val amount: Double,
    val currency: String,
    val paymentMethodId: Long,
    val paymentMethodLabel: String = "",
    val startDate: String?,
    val status: SubscriptionStatus,
    val categoryId: Long,
    val categoryLabel: String = "",
    val renewalType: RenewalType,
    val notes: String?,
    val website: String?,
    val createdAt: String = "",
    val updatedAt: String = ""
)

enum class BillingCycle {
    MONTHLY, YEARLY, QUARTERLY;
    
    fun toDbValue(): String = name.lowercase()
    
    companion object {
        fun fromDbValue(value: String): BillingCycle = 
            valueOf(value.uppercase())
    }
}

enum class SubscriptionStatus {
    ACTIVE, INACTIVE, CANCELLED;
    
    fun toDbValue(): String = name.lowercase()
    
    companion object {
        fun fromDbValue(value: String): SubscriptionStatus = 
            valueOf(value.uppercase())
    }
}

enum class RenewalType {
    AUTO, MANUAL;
    
    fun toDbValue(): String = name.lowercase()
    
    companion object {
        fun fromDbValue(value: String): RenewalType = 
            valueOf(value.uppercase())
    }
}