package com.alaa.alaagallo.view.my_accounts_and_sale.composable_screen.salesOptions.costs

import com.alaa.alaagallo.view.my_accounts_and_sale.base.BaseViewModel
import com.alaa.alaagallo.view.my_accounts_and_sale.base.ErrorUiState
import com.alaa.alaagallo.view.my_accounts_and_sale.composable_screen.salesOptions.customers.Customer
import com.alaa.alaagallo.view.my_accounts_and_sale.composable_screen.salesOptions.customers.CustomersUiEffect
import com.alaa.domain.usecase.ManageExpensesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import javax.inject.Inject

@HiltViewModel
class CostViewModel @Inject constructor(
    private val manageCostUseCase: ManageExpensesUseCase
) :
    BaseViewModel<CostUiState, CostUiEffect>(CostUiState()) {

    init {
        getAllCost()
    }

    private fun getAllCost() {
        updateState { it.copy(isLoading = true) }
        tryToExecute(
            function = { manageCostUseCase.getAllExpenseses() },
            onSuccess = { expenses ->
                updateState {
                    it.copy(
                        isLoading = false,
                        costs = expenses.map {
                            Cost(
                                id = it.id,
                                name = it.exchange_party_name,
                                date = it.date,
                                amount = it.amount.toString()
                            )
                        }
                    )
                }
            },
            onError = ::onError
        )
    }

    private fun onEditCost() {
        updateState { it.copy(isDoneLoading = true) }
        tryToExecute(
            function = {
                manageCostUseCase.updateExpenseses(
                    id = state.value.editValues!!.id,
                    exchangePartyName = state.value.name,
                    date = state.value.date,
                    amount = state.value.amount
                )
            },
            onSuccess = {
                sendNewEffect(CostUiEffect.ShowToastSuccessEditingCost)
                reset()
                getAllCost()
            },
            onError = ::onError
        )
    }

    private fun onAddCost() {
        updateState { it.copy(isDoneLoading = true) }
        tryToExecute(
            function = {
                manageCostUseCase.addExpenseses(
                    exchangePartyName = state.value.name,
                    date = state.value.date,
                    amount = state.value.amount
                )
            },
            onSuccess = {
                sendNewEffect(CostUiEffect.ShowToastSuccessAddingCost)
                reset()
                getAllCost()
            },
            onError = ::onError
        )
    }

    fun onClickDelete(currentCost: Cost) {
        updateState { it.copy(isLoading = true) }
        tryToExecute(
            function = {
                manageCostUseCase.deleteExpenseses(
                    id = currentCost.id.toIntOrNull() ?: 0
                )
            },
            onSuccess = {
                val newCustomers = state.value.costs.toMutableList()
                newCustomers.removeIf { it.id == currentCost.id }
                updateState {
                    it.copy(
                        isLoading = false,
                        costs = newCustomers
                    )
                }
            },
            onError = ::onError
        )
    }


    private fun reset() {
        updateState {
            it.copy(
                name = "",
                amount = "",
                date = "",
                editValues = null,
                isEdit = false,
                isAddingNew = false,
                isDoneLoading = false,
                isLoading = false
            )
        }
    }

    fun onChangeSearchValue(value: String) {
        updateState { it.copy(searchValue = value) }
    }

    fun navigateToAddNew() {
        reset()
        updateState { it.copy(isAddingNew = true) }
    }

    fun navigateBackToDetails() {
        if (state.value.isAddingNew)
            updateState { it.copy(isAddingNew = false) }
        else
            sendNewEffect(CostUiEffect.NavigateBack)

    }

    fun onChangeNameValue(value: String) {
        updateState { it.copy(name = value) }
    }

    fun onChangeDateValue(value: String) {
        updateState { it.copy(date = value) }
    }

    fun onChangeAmountValue(value: String) {
        updateState { it.copy(amount = value) }
    }


    fun onClickDone() {
        if (state.value.isEdit) {
            onEditCost()
        } else {
            onAddCost()
        }
    }

    fun onClickItem(cost: Cost) {
        updateState {
            it.copy(
                name = cost.name,
                date = cost.date,
                amount = cost.amount,
                editValues = cost,
                isEdit = true,
                isAddingNew = true
            )
        }
    }


    private fun onError(error: ErrorUiState, message: String) {
        updateState {
            it.copy(isLoading = false, isDoneLoading = false, error = message)
        }
        sendNewEffect(CostUiEffect.ShowToastError(message))
    }
}