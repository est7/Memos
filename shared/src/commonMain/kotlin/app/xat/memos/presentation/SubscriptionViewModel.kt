package app.xat.memos.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import app.xat.memos.domain.model.Category
import app.xat.memos.domain.model.PaymentMethod
import app.xat.memos.domain.model.Subscription
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
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch

/**
 * ViewModel for subscription management
 *
 * @author: est8
 * @date: 7/25/25
 */
class SubscriptionViewModel(
    private val getAllSubscriptionsUseCase: GetAllSubscriptionsUseCase,
    private val getSubscriptionByIdUseCase: GetSubscriptionByIdUseCase,
    private val getActiveSubscriptionsUseCase: GetActiveSubscriptionsUseCase,
    private val insertSubscriptionUseCase: InsertSubscriptionUseCase,
    private val updateSubscriptionUseCase: UpdateSubscriptionUseCase,
    private val deleteSubscriptionUseCase: DeleteSubscriptionUseCase,
    private val getAllCategoriesUseCase: GetAllCategoriesUseCase,
    private val getAllPaymentMethodsUseCase: GetAllPaymentMethodsUseCase,
    private val getTotalMonthlySpendingUseCase: GetTotalMonthlySpendingUseCase,
    private val getTotalYearlySpendingUseCase: GetTotalYearlySpendingUseCase,
    private val getUpcomingBillingsUseCase: GetUpcomingBillingsUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(SubscriptionUiState())
    val uiState: StateFlow<SubscriptionUiState> = _uiState.asStateFlow()

    init {
        loadInitialData()
    }

    private fun loadInitialData() {
        loadSubscriptions()
        loadCategories()
        loadPaymentMethods()
        loadStatistics()
    }

    fun loadSubscriptions() {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isLoading = true)
            try {
                getAllSubscriptionsUseCase().catch { error ->
                    _uiState.value = _uiState.value.copy(
                        isLoading = false,
                        error = error.message
                    )
                }.collect { subscriptions ->
                    _uiState.value = _uiState.value.copy(
                        subscriptions = subscriptions,
                        isLoading = false,
                        error = null
                    )
                }
            } catch (e: Exception) {
                _uiState.value = _uiState.value.copy(
                    isLoading = false,
                    error = e.message
                )
            }
        }
    }

    fun loadActiveSubscriptions() {
        viewModelScope.launch {
            try {
                getActiveSubscriptionsUseCase().catch { error ->
                    _uiState.value = _uiState.value.copy(error = error.message)
                }.collect { activeSubscriptions ->
                    _uiState.value = _uiState.value.copy(
                        activeSubscriptions = activeSubscriptions,
                        error = null
                    )
                }
            } catch (e: Exception) {
                _uiState.value = _uiState.value.copy(error = e.message)
            }
        }
    }

    fun loadCategories() {
        viewModelScope.launch {
            try {
                getAllCategoriesUseCase().catch { error ->
                    _uiState.value = _uiState.value.copy(error = error.message)
                }.collect { categories ->
                    _uiState.value = _uiState.value.copy(
                        categories = categories,
                        error = null
                    )
                }
            } catch (e: Exception) {
                _uiState.value = _uiState.value.copy(error = e.message)
            }
        }
    }

    fun loadPaymentMethods() {
        viewModelScope.launch {
            try {
                getAllPaymentMethodsUseCase().catch { error ->
                    _uiState.value = _uiState.value.copy(error = error.message)
                }.collect { paymentMethods ->
                    _uiState.value = _uiState.value.copy(
                        paymentMethods = paymentMethods,
                        error = null
                    )
                }
            } catch (e: Exception) {
                _uiState.value = _uiState.value.copy(error = e.message)
            }
        }
    }

    fun loadStatistics() {
        viewModelScope.launch {
            try {
                val monthlySpending = getTotalMonthlySpendingUseCase()
                val yearlySpending = getTotalYearlySpendingUseCase()
                
                getUpcomingBillingsUseCase().catch { error ->
                    _uiState.value = _uiState.value.copy(error = error.message)
                }.collect { upcomingBillings ->
                    _uiState.value = _uiState.value.copy(
                        monthlySpending = monthlySpending,
                        yearlySpending = yearlySpending,
                        upcomingBillings = upcomingBillings,
                        error = null
                    )
                }
            } catch (e: Exception) {
                _uiState.value = _uiState.value.copy(error = e.message)
            }
        }
    }

    fun insertSubscription(subscription: Subscription) {
        viewModelScope.launch {
            try {
                insertSubscriptionUseCase(subscription)
                loadSubscriptions() // Refresh the list
            } catch (e: Exception) {
                _uiState.value = _uiState.value.copy(error = e.message)
            }
        }
    }

    fun updateSubscription(subscription: Subscription) {
        viewModelScope.launch {
            try {
                updateSubscriptionUseCase(subscription)
                loadSubscriptions() // Refresh the list
            } catch (e: Exception) {
                _uiState.value = _uiState.value.copy(error = e.message)
            }
        }
    }

    fun deleteSubscription(id: Long) {
        viewModelScope.launch {
            try {
                deleteSubscriptionUseCase(id)
                loadSubscriptions() // Refresh the list
            } catch (e: Exception) {
                _uiState.value = _uiState.value.copy(error = e.message)
            }
        }
    }

    fun getSubscriptionById(id: Long) {
        viewModelScope.launch {
            try {
                val subscription = getSubscriptionByIdUseCase(id)
                _uiState.value = _uiState.value.copy(
                    selectedSubscription = subscription,
                    error = null
                )
            } catch (e: Exception) {
                _uiState.value = _uiState.value.copy(error = e.message)
            }
        }
    }

    fun clearError() {
        _uiState.value = _uiState.value.copy(error = null)
    }

    fun clearSelectedSubscription() {
        _uiState.value = _uiState.value.copy(selectedSubscription = null)
    }
}

data class SubscriptionUiState(
    val subscriptions: List<Subscription> = emptyList(),
    val activeSubscriptions: List<Subscription> = emptyList(),
    val categories: List<Category> = emptyList(),
    val paymentMethods: List<PaymentMethod> = emptyList(),
    val upcomingBillings: List<Subscription> = emptyList(),
    val selectedSubscription: Subscription? = null,
    val monthlySpending: Double = 0.0,
    val yearlySpending: Double = 0.0,
    val isLoading: Boolean = false,
    val error: String? = null
)