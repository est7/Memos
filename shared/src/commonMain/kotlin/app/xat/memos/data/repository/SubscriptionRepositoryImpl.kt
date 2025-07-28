package app.xat.memos.data.repository

import app.xat.memos.data.local.SubscriptionLocalDataSource
import app.xat.memos.domain.model.Category
import app.xat.memos.domain.model.PaymentMethod
import app.xat.memos.domain.model.Subscription
import app.xat.memos.domain.repository.SubscriptionRepository
import kotlinx.coroutines.flow.Flow

/**
 * Implementation of SubscriptionRepository
 *
 * @author: est8
 * @date: 7/25/25
 */
class SubscriptionRepositoryImpl(
    private val localDataSource: SubscriptionLocalDataSource
) : SubscriptionRepository {
    
    override suspend fun getAllSubscriptions(): Flow<List<Subscription>> {
        return localDataSource.getAllSubscriptions()
    }
    
    override suspend fun getSubscriptionById(id: Long): Subscription? {
        return localDataSource.getSubscriptionById(id)
    }
    
    override suspend fun getActiveSubscriptions(): Flow<List<Subscription>> {
        return localDataSource.getActiveSubscriptions()
    }
    
    override suspend fun getSubscriptionsByCategory(categoryId: Long): Flow<List<Subscription>> {
        return localDataSource.getSubscriptionsByCategory(categoryId)
    }
    
    override suspend fun insertSubscription(subscription: Subscription): Long {
        return localDataSource.insertSubscription(subscription)
    }
    
    override suspend fun updateSubscription(subscription: Subscription) {
        localDataSource.updateSubscription(subscription)
    }
    
    override suspend fun deleteSubscription(id: Long) {
        localDataSource.deleteSubscription(id)
    }
    
    override suspend fun getAllCategories(): Flow<List<Category>> {
        return localDataSource.getAllCategories()
    }
    
    override suspend fun getCategoryById(id: Long): Category? {
        return localDataSource.getCategoryById(id)
    }
    
    override suspend fun insertCategory(category: Category): Long {
        return localDataSource.insertCategory(category)
    }
    
    override suspend fun updateCategory(category: Category) {
        localDataSource.updateCategory(category)
    }
    
    override suspend fun deleteCategory(id: Long) {
        localDataSource.deleteCategory(id)
    }
    
    override suspend fun getAllPaymentMethods(): Flow<List<PaymentMethod>> {
        return localDataSource.getAllPaymentMethods()
    }
    
    override suspend fun getPaymentMethodById(id: Long): PaymentMethod? {
        return localDataSource.getPaymentMethodById(id)
    }
    
    override suspend fun insertPaymentMethod(paymentMethod: PaymentMethod): Long {
        return localDataSource.insertPaymentMethod(paymentMethod)
    }
    
    override suspend fun updatePaymentMethod(paymentMethod: PaymentMethod) {
        localDataSource.updatePaymentMethod(paymentMethod)
    }
    
    override suspend fun deletePaymentMethod(id: Long) {
        localDataSource.deletePaymentMethod(id)
    }
    
    override suspend fun getTotalMonthlySpending(): Double {
        return localDataSource.getTotalMonthlySpending()
    }
    
    override suspend fun getTotalYearlySpending(): Double {
        return localDataSource.getTotalYearlySpending()
    }
    
    override suspend fun getUpcomingBillings(): Flow<List<Subscription>> {
        return localDataSource.getUpcomingBillings()
    }
}