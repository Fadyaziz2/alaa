package com.alaa.alaagallo.view.my_accounts_and_sale.composable_screen.salesOptions.damaged

import com.alaa.alaagallo.view.my_accounts_and_sale.base.BaseViewModel
import com.alaa.alaagallo.view.my_accounts_and_sale.base.ErrorUiState
import com.alaa.domain.entity.ProductEntity
import com.alaa.domain.usecase.ManagePerishesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import javax.inject.Inject

@HiltViewModel
class DamagedViewModel @Inject constructor(
    private val managePerishesUseCase: ManagePerishesUseCase
) :
    BaseViewModel<DamagedUiState, DamagedUiEffect>(DamagedUiState()) {
    init {
        getAllCost()
    }

    private fun getAllCost() {
        updateState { it.copy(isLoading = true) }
        tryToExecute(
            function = { managePerishesUseCase.getAllPerishes() },
            onSuccess = { items ->
                updateState {
                    it.copy(
                        isLoading = false,
                        damaged = items
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
                val updatedValues = state.value.editValues!!
                managePerishesUseCase.updatePerishes(
                    id = updatedValues.id,
                    name = state.value.name,
                    price = state.value.price.toLongOrNull() ?: 0L,
                    date = state.value.date
                )
            },
            onSuccess = {
                sendNewEffect(DamagedUiEffect.ShowToastSuccessEditingDamaged)
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
                managePerishesUseCase.addPerishes(
                    name = state.value.name,
                    price = state.value.price.toLongOrNull() ?: 0L,
                    date = state.value.date
                )
            },
            onSuccess = {
                sendNewEffect(DamagedUiEffect.ShowToastSuccessAddingDamaged)
                reset()
                getAllCost()
            },
            onError = ::onError
        )
    }

    fun onClickDelete(currentDamaged: ProductEntity) {
        updateState { it.copy(isLoading = true) }
        tryToExecute(
            function = {
                managePerishesUseCase.deletePerishes(
                    currentDamaged.id.toIntOrNull() ?: -1
                )
            },
            onSuccess = {
                val newDamaged = state.value.damaged.toMutableList()
                newDamaged.removeIf { it.id == currentDamaged.id }
                updateState {
                    it.copy(
                        isLoading = false,
                        damaged = newDamaged
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
                price = "",
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
            sendNewEffect(DamagedUiEffect.NavigateBack)

    }

    fun onChangeNameValue(value: String) {
        updateState { it.copy(name = value) }
    }

    fun onChangeDateValue(value: String) {
        updateState { it.copy(date = value) }
    }

    fun onChangeAmountValue(value: String) {
        updateState { it.copy(price = value) }
    }


    fun onClickDone() {
        if (state.value.isEdit) {
            onEditCost()
        } else {
            onAddCost()
        }
    }

    fun onClickItem(damaged: ProductEntity) {
        updateState {
            it.copy(
                name = damaged.name,
                date = damaged.date,
                price = damaged.price.toString(),
                editValues = damaged,
                isEdit = true,
                isAddingNew = true
            )
        }
    }


    private fun onError(error: ErrorUiState, message: String) {
        updateState {
            it.copy(isLoading = false, isDoneLoading = false, error = message)
        }
        sendNewEffect(DamagedUiEffect.ShowToastError(message))
    }
}