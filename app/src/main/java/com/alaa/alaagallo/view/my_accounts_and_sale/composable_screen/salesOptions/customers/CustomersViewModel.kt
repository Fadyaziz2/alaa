package com.alaa.alaagallo.view.my_accounts_and_sale.composable_screen.salesOptions.customers

import android.util.Log
import com.alaa.alaagallo.view.my_accounts_and_sale.base.BaseViewModel
import com.alaa.alaagallo.view.my_accounts_and_sale.base.ErrorUiState
import com.alaa.domain.usecase.ManageUserUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import javax.inject.Inject

@HiltViewModel
class CustomersViewModel @Inject constructor(
    private val manageUserUseCase: ManageUserUseCase
) :
    BaseViewModel<CustomersUiState, CustomersUiEffect>(CustomersUiState()) {

    init {
        getAllCustomer()
    }

    private fun getAllCustomer() {
        updateState { it.copy(isLoading = true) }
        tryToExecute(
            function = { manageUserUseCase.getAllCustomerUsers() },
            onSuccess = { customers ->
                updateState {
                    it.copy(
                        isLoading = false,
                        customers = customers.map { customer ->
                            Customer(
                                name = customer.name,
                                phone = customer.mobile,
                                costs = customer.type,
                                details = customer.note,
                                id = customer.id.toString()
                            )
                        }
                    )
                }
            },
            onError = ::onError
        )
    }

    private fun onEditCustomer() {
        updateState { it.copy(isDoneLoading = true) }
        tryToExecute(
            function = {
                manageUserUseCase.updateCustomerUser(
                    id = state.value.editValues!!.id,
                    name = state.value.name,
                    mobile = state.value.phone,
                    note = state.value.details
                )
            },
            onSuccess = {
                sendNewEffect(CustomersUiEffect.ShowToastSuccessEditingCustomer)
                reset()
                getAllCustomer()
            },
            onError = ::onError
        )
    }

    private fun onAddCustomer() {
        updateState { it.copy(isDoneLoading = true) }
        tryToExecute(
            function = {
                manageUserUseCase.addCustomerUser(
                    name = state.value.name,
                    mobile = state.value.phone,
                    status = state.value.details
                )
            },
            onSuccess = {
                sendNewEffect(CustomersUiEffect.ShowToastSuccessAddingCustomer)
                reset()
                getAllCustomer()
            },
            onError = ::onError
        )
    }

    fun onClickDelete(currentCustomer: Customer) {
        updateState { it.copy(isLoading = true) }
        tryToExecute(
            function = {
                manageUserUseCase.deleteUser(
                    id = currentCustomer.id.toInt()
                )
            },
            onSuccess = {
                val newCustomers = state.value.customers.toMutableList()
                newCustomers.removeIf { it.id == currentCustomer.id }
                updateState {
                    it.copy(
                        isLoading = false,
                        customers = newCustomers
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
                phone = "",
                costs = "",
                details = "",
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
            sendNewEffect(CustomersUiEffect.NavigateBack)

    }

    fun onChangeNameValue(value: String) {
        updateState { it.copy(name = value) }
    }

    fun onChangePhoneValue(value: String) {
        updateState { it.copy(phone = value) }
    }

    fun onChangeCostsValue(value: String) {
        updateState { it.copy(costs = value) }
    }

    fun onChangeDetailsValue(value: String) {
        updateState { it.copy(details = value) }
    }

    fun onClickDone() {
        if (state.value.isEdit) {
            onEditCustomer()
        } else {
            onAddCustomer()
        }
    }

    fun onClickItem(customer: Customer) {
        updateState {
            it.copy(
                name = customer.name,
                phone = customer.phone,
                costs = customer.costs,
                details = customer.details,
                editValues = customer,
                isEdit = true,
                isAddingNew = true
            )
        }
    }


    private fun onError(error: ErrorUiState, message: String) {
        updateState {
            it.copy(isLoading = false, isDoneLoading = false, error = message)
        }
        sendNewEffect(CustomersUiEffect.ShowToastError(message))
    }
}