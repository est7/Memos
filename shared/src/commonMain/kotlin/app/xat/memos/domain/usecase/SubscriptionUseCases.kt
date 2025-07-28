package app.xat.memos.domain.usecase

import app.xat.memos.domain.model.Category
import app.xat.memos.domain.model.PaymentMethod
import app.xat.memos.domain.model.Subscription
import app.xat.memos.domain.repository.SubscriptionRepository
import kotlinx.coroutines.flow.Flow

/**
 * Use cases for subscription operations
 *
 * @author: est8
 * @date: 7/25/25
 */
class GetAllSubscriptionsUseCase(
    private val repository: SubscriptionRepository
) {
    suspend operator fun invoke(): Flow<List<Subscription>> {
        return repository.getAllSubscriptions()
    }
}

class GetSubscriptionByIdUseCase(
    private val repository: SubscriptionRepository
) {
    suspend operator fun invoke(id: Long): Subscription? {
        return repository.getSubscriptionById(id)
    }
}

class GetActiveSubscriptionsUseCase(
    private val repository: SubscriptionRepository
) {
    suspend operator fun invoke(): Flow<List<Subscription>> {
        return repository.getActiveSubscriptions()
    }
}

class GetSubscriptionsByCategoryUseCase(
    private val repository: SubscriptionRepository
) {
    suspend operator fun invoke(categoryId: Long): Flow<List<Subscription>> {
        return repository.getSubscriptionsByCategory(categoryId)
    }
}

class InsertSubscriptionUseCase(
    private val repository: SubscriptionRepository
) {
    suspend operator fun invoke(subscription: Subscription): Long {
        return repository.insertSubscription(subscription)
    }
}

class UpdateSubscriptionUseCase(
    private val repository: SubscriptionRepository
) {
    suspend operator fun invoke(subscription: Subscription) {
        repository.updateSubscription(subscription)
    }
}

class DeleteSubscriptionUseCase(
    private val repository: SubscriptionRepository
) {
    suspend operator fun invoke(id: Long) {
        repository.deleteSubscription(id)
    }
}

class GetAllCategoriesUseCase(
    private val repository: SubscriptionRepository
) {
    suspend operator fun invoke(): Flow<List<Category>> {
        return repository.getAllCategories()
    }
}

class GetAllPaymentMethodsUseCase(
    private val repository: SubscriptionRepository
) {
    suspend operator fun invoke(): Flow<List<PaymentMethod>> {
        return repository.getAllPaymentMethods()
    }
}

class GetUpcomingBillingsUseCase(
    private val repository: SubscriptionRepository
) {
    suspend operator fun invoke(): Flow<List<Subscription>> {
        return repository.getUpcomingBillings()
    }
}

class GetTotalMonthlySpendingUseCase(
    private val repository: SubscriptionRepository
) {
    suspend operator fun invoke(): Double {
        return repository.getTotalMonthlySpending()
    }
}

class GetTotalYearlySpendingUseCase(
    private val repository: SubscriptionRepository
) {
    suspend operator fun invoke(): Double {
        return repository.getTotalYearlySpending()
    }
}