package app.xat.memos.data.local

import app.xat.memos.domain.model.BillingCycle
import app.xat.memos.domain.model.Category
import app.xat.memos.domain.model.PaymentMethod
import app.xat.memos.domain.model.RenewalType
import app.xat.memos.domain.model.Subscription
import app.xat.memos.domain.model.SubscriptionStatus
import app.xat.memos.shared.AppDatabase
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf

/**
 * Local data source for subscription operations using SQLDelight
 *
 * @author: est8
 * @date: 7/25/25
 */
class SubscriptionLocalDataSource(
    private val database: AppDatabase
) {
    
    // Subscription operations
    fun getAllSubscriptions(): Flow<List<Subscription>> {
        // TODO: Implement with proper SQLDelight queries once generated classes are available
        return flowOf(
            listOf(
                Subscription(
                    id = 1,
                    name = "Netflix",
                    plan = "Premium",
                    billingCycle = BillingCycle.MONTHLY,
                    nextBillingDate = "2025-08-25",
                    lastBillingDate = "2025-07-25",
                    amount = 15.99,
                    currency = "USD",
                    paymentMethodId = 1,
                    paymentMethodLabel = "Credit Card",
                    startDate = "2025-01-01",
                    status = SubscriptionStatus.ACTIVE,
                    categoryId = 1,
                    categoryLabel = "Entertainment",
                    renewalType = RenewalType.AUTO,
                    notes = "Family plan",
                    website = "https://netflix.com"
                )
            )
        )
    }
    
    suspend fun getSubscriptionById(id: Long): Subscription? {
        // TODO: Implement with proper SQLDelight queries
        return if (id == 1L) {
            Subscription(
                id = 1,
                name = "Netflix",
                plan = "Premium",
                billingCycle = BillingCycle.MONTHLY,
                nextBillingDate = "2025-08-25",
                lastBillingDate = "2025-07-25",
                amount = 15.99,
                currency = "USD",
                paymentMethodId = 1,
                paymentMethodLabel = "Credit Card",
                startDate = "2025-01-01",
                status = SubscriptionStatus.ACTIVE,
                categoryId = 1,
                categoryLabel = "Entertainment",
                renewalType = RenewalType.AUTO,
                notes = "Family plan",
                website = "https://netflix.com"
            )
        } else null
    }
    
    fun getActiveSubscriptions(): Flow<List<Subscription>> {
        // TODO: Implement with proper SQLDelight queries
        return getAllSubscriptions()
    }
    
    fun getSubscriptionsByCategory(categoryId: Long): Flow<List<Subscription>> {
        // TODO: Implement with proper SQLDelight queries
        return getAllSubscriptions()
    }
    
    suspend fun insertSubscription(subscription: Subscription): Long {
        // TODO: Implement with proper SQLDelight queries
        return subscription.id
    }
    
    suspend fun updateSubscription(subscription: Subscription) {
        // TODO: Implement with proper SQLDelight queries
    }
    
    suspend fun deleteSubscription(id: Long) {
        // TODO: Implement with proper SQLDelight queries
    }
    
    // Category operations
    fun getAllCategories(): Flow<List<Category>> {
        // TODO: Implement with proper SQLDelight queries
        return flowOf(
            listOf(
                Category(id = 1, value = "entertainment", label = "Entertainment"),
                Category(id = 2, value = "productivity", label = "Productivity"),
                Category(id = 3, value = "utilities", label = "Utilities")
            )
        )
    }
    
    suspend fun getCategoryById(id: Long): Category? {
        // TODO: Implement with proper SQLDelight queries
        return Category(id = id, value = "entertainment", label = "Entertainment")
    }
    
    suspend fun insertCategory(category: Category): Long {
        // TODO: Implement with proper SQLDelight queries
        return category.id
    }
    
    suspend fun updateCategory(category: Category) {
        // TODO: Implement with proper SQLDelight queries
    }
    
    suspend fun deleteCategory(id: Long) {
        // TODO: Implement with proper SQLDelight queries
    }
    
    // Payment method operations
    fun getAllPaymentMethods(): Flow<List<PaymentMethod>> {
        // TODO: Implement with proper SQLDelight queries
        return flowOf(
            listOf(
                PaymentMethod(id = 1, value = "credit_card", label = "Credit Card"),
                PaymentMethod(id = 2, value = "debit_card", label = "Debit Card"),
                PaymentMethod(id = 3, value = "paypal", label = "PayPal")
            )
        )
    }
    
    suspend fun getPaymentMethodById(id: Long): PaymentMethod? {
        // TODO: Implement with proper SQLDelight queries
        return PaymentMethod(id = id, value = "credit_card", label = "Credit Card")
    }
    
    suspend fun insertPaymentMethod(paymentMethod: PaymentMethod): Long {
        // TODO: Implement with proper SQLDelight queries
        return paymentMethod.id
    }
    
    suspend fun updatePaymentMethod(paymentMethod: PaymentMethod) {
        // TODO: Implement with proper SQLDelight queries
    }
    
    suspend fun deletePaymentMethod(id: Long) {
        // TODO: Implement with proper SQLDelight queries
    }
    
    // Statistics
    suspend fun getTotalMonthlySpending(): Double {
        // TODO: Implement with proper SQLDelight queries
        return 45.97
    }
    
    suspend fun getTotalYearlySpending(): Double {
        // TODO: Implement with proper SQLDelight queries
        return 551.64
    }
    
    fun getUpcomingBillings(): Flow<List<Subscription>> {
        // TODO: Implement with proper SQLDelight queries
        return getAllSubscriptions()
    }
}