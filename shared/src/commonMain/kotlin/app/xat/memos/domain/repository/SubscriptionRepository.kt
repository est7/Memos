package app.xat.memos.domain.repository

import app.xat.memos.domain.model.Category
import app.xat.memos.domain.model.PaymentMethod
import app.xat.memos.domain.model.Subscription
import app.xat.memos.domain.model.SubscriptionStatus
import kotlinx.coroutines.flow.Flow

/**
 * Repository interface for subscription operations
 *
 * @author: est8
 * @date: 7/25/25
 */
interface SubscriptionRepository {
    
    // Subscription CRUD operations
    suspend fun getAllSubscriptions(): Flow<List<Subscription>>
    suspend fun getSubscriptionById(id: Long): Subscription?
    suspend fun getActiveSubscriptions(): Flow<List<Subscription>>
    suspend fun getSubscriptionsByCategory(categoryId: Long): Flow<List<Subscription>>
    suspend fun insertSubscription(subscription: Subscription): Long
    suspend fun updateSubscription(subscription: Subscription)
    suspend fun deleteSubscription(id: Long)
    
    // Category operations
    suspend fun getAllCategories(): Flow<List<Category>>
    suspend fun getCategoryById(id: Long): Category?
    suspend fun insertCategory(category: Category): Long
    suspend fun updateCategory(category: Category)
    suspend fun deleteCategory(id: Long)
    
    // Payment method operations
    suspend fun getAllPaymentMethods(): Flow<List<PaymentMethod>>
    suspend fun getPaymentMethodById(id: Long): PaymentMethod?
    suspend fun insertPaymentMethod(paymentMethod: PaymentMethod): Long
    suspend fun updatePaymentMethod(paymentMethod: PaymentMethod)
    suspend fun deletePaymentMethod(id: Long)
    
    // Statistics
    suspend fun getTotalMonthlySpending(): Double
    suspend fun getTotalYearlySpending(): Double
    suspend fun getUpcomingBillings(): Flow<List<Subscription>>
}