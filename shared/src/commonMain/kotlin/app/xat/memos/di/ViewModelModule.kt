package app.xat.memos.di

import app.xat.memos.data.local.SubscriptionLocalDataSource
import app.xat.memos.data.repository.SubscriptionRepositoryImpl
import app.xat.memos.domain.repository.SubscriptionRepository
import app.xat.memos.domain.usecase.DeleteSubscriptionUseCase
import app.xat.memos.domain.usecase.GetActiveSubscriptionsUseCase
import app.xat.memos.domain.usecase.GetAllCategoriesUseCase
import app.xat.memos.domain.usecase.GetAllPaymentMethodsUseCase
import app.xat.memos.domain.usecase.GetAllSubscriptionsUseCase
import app.xat.memos.domain.usecase.GetSubscriptionByIdUseCase
import app.xat.memos.domain.usecase.GetTotalMonthlySpendingUseCase
import app.xat.memos.domain.usecase.GetTotalYearlySpendingUseCase
import app.xat.memos.domain.usecase.GetUpcomingBillingsUseCase
import app.xat.memos.domain.usecase.InsertSubscriptionUseCase
import app.xat.memos.domain.usecase.UpdateSubscriptionUseCase
import app.xat.memos.presentation.SubscriptionViewModel
import app.xat.memos.shared.AppDatabase
import app.xat.memos.shared.DatabaseDriverFactory
import org.koin.dsl.module

/**
 * Koin module for dependency injection
 *
 * @author: est8
 * @date: 7/24/25
 */
val viewModelModule = module {
    // Use Cases
    factory { GetAllSubscriptionsUseCase(get()) }
    factory { GetSubscriptionByIdUseCase(get()) }
    factory { GetActiveSubscriptionsUseCase(get()) }
    factory { InsertSubscriptionUseCase(get()) }
    factory { UpdateSubscriptionUseCase(get()) }
    factory { DeleteSubscriptionUseCase(get()) }
    factory { GetAllCategoriesUseCase(get()) }
    factory { GetAllPaymentMethodsUseCase(get()) }
    factory { GetTotalMonthlySpendingUseCase(get()) }
    factory { GetTotalYearlySpendingUseCase(get()) }
    factory { GetUpcomingBillingsUseCase(get()) }
    
    // ViewModel
    factory { 
        SubscriptionViewModel(
            getAllSubscriptionsUseCase = get(),
            getSubscriptionByIdUseCase = get(),
            getActiveSubscriptionsUseCase = get(),
            insertSubscriptionUseCase = get(),
            updateSubscriptionUseCase = get(),
            deleteSubscriptionUseCase = get(),
            getAllCategoriesUseCase = get(),
            getAllPaymentMethodsUseCase = get(),
            getTotalMonthlySpendingUseCase = get(),
            getTotalYearlySpendingUseCase = get(),
            getUpcomingBillingsUseCase = get()
        )
    }
}