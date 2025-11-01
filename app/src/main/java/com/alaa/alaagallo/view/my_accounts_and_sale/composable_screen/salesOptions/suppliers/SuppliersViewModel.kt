package com.alaa.alaagallo.view.my_accounts_and_sale.composable_screen.salesOptions.suppliers

import com.alaa.alaagallo.view.my_accounts_and_sale.base.BaseViewModel
import com.alaa.alaagallo.view.my_accounts_and_sale.base.ErrorUiState
import com.alaa.alaagallo.view.my_accounts_and_sale.composable_screen.salesOptions.customers.Customer
import com.alaa.domain.usecase.ManageUserUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import javax.inject.Inject

@HiltViewModel
class SuppliersViewModel @Inject constructor(
    private val manageUserUseCase: ManageUserUseCase
) :
    BaseViewModel<SuppliersUiState, SuppliersUiEffect>(SuppliersUiState()) {

    init {
        getAllSupplier()
    }
    private fun getAllSupplier() {
        updateState { it.copy(isLoading = true) }
        tryToExecute(
            function = { manageUserUseCase.getAllSupplierUsers() },
            onSuccess = { suppliers ->
                updateState {
                    it.copy(
                        isLoading = false,
                        suppliers = suppliers.map { supplier ->
                            Supplier(
                                name = supplier.name,
                                phone = supplier.mobile,
                                costs = supplier.type,
                                details = supplier.note,
                                id = supplier.id.toString()
                            )
                        }
                    )
                }
            },
            onError = ::onError
        )
    }


    private fun onEditSupplier() {
        updateState { it.copy(isDoneLoading = true) }
        tryToExecute(
            function = {
                manageUserUseCase.updateSupplierUser(
                    id = state.value.editValues!!.id,
                    name = state.value.name,
                    mobile = state.value.phone,
                    note = state.value.details
                )
            },
            onSuccess = {
                sendNewEffect(SuppliersUiEffect.ShowToastSuccessEditingSupplier)
                reset()
                getAllSupplier()
            },
            onError = ::onError
        )
    }

    private fun onAddSupplier() {
        updateState { it.copy(isDoneLoading = true) }
        tryToExecute(
            function = {
                manageUserUseCase.addSupplierUser(
                    name = state.value.name,
                    mobile = state.value.phone,
                    note = state.value.details
                )
            },
            onSuccess = {
                sendNewEffect(SuppliersUiEffect.ShowToastSuccessAddingSupplier)
                reset()
                getAllSupplier()
            },
            onError = ::onError
        )
    }

    fun onClickDelete(currentSupplier: Supplier) {
        updateState { it.copy(isLoading = true) }
        tryToExecute(
            function = {
                manageUserUseCase.deleteUser(
                   id = currentSupplier.id.toInt()
                )
            },
            onSuccess = {
                val newSuppliers = state.value.suppliers.toMutableList()
                newSuppliers.removeIf { it.id == currentSupplier.id }
                updateState {
                    it.copy(
                        isLoading = false,
                        suppliers = newSuppliers
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
            sendNewEffect(SuppliersUiEffect.NavigateBack)
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
            onEditSupplier()
        } else {
            onAddSupplier()
        }
    }

    fun onClickItem(supplier: Supplier) {
        updateState {
            it.copy(
                name = supplier.name,
                phone = supplier.phone,
                costs = supplier.costs,
                details = supplier.details,
                editValues = supplier,
                isEdit = true,
                isAddingNew = true
            )
        }
    }


    private fun onError(error: ErrorUiState, message: String) {
        updateState {
            it.copy(isLoading = false, isDoneLoading = false, error = message)
        }
        sendNewEffect(SuppliersUiEffect.ShowToastError(message))
    }
}