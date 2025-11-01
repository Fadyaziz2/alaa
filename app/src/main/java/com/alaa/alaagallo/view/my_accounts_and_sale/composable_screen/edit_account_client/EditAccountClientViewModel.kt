package com.alaa.alaagallo.view.my_accounts_and_sale.composable_screen.edit_account_client

import androidx.lifecycle.SavedStateHandle
import com.alaa.alaagallo.view.my_accounts_and_sale.base.BaseViewModel
import com.alaa.domain.usecase.ManageAccountsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class EditAccountClientViewModel @Inject constructor(
    private val manageAccountsUseCase: ManageAccountsUseCase,
    savedStateHandle: SavedStateHandle,
) : BaseViewModel<EditAccountClientState, EditAccountClientEffect>(EditAccountClientState()) {
    private val args = EditAccountClientArgs(savedStateHandle)

    init {
        updateState {
            it.copy(
                clientId = args.clientId,
                basePhoneNumber = args.phoneNumber,
                baseName = args.name,
                baseSelectedCategoryName = args.categoryName,
                baseCategoryId = args.categoryId,
                name = args.name,
                phoneNumber = args.phoneNumber,
                selectedCategoryName = args.categoryName,
            )
        }
        getCategories()
    }

    private fun getCategories() {
        updateState {
            it.copy(isLoading = true)
        }
        tryToExecute(
            function = {
                manageAccountsUseCase.getCategoriesAndUsers()
            },
            onSuccess = { data ->
                updateState {
                    it.copy(
                        isLoading = false,
                        categories = data,
                        selectedCategoryPosition = data.indexOfFirst { category -> category.id == state.value.baseCategoryId }
                    )
                }
            },
            onError = { _, m ->
                updateState {
                    it.copy(isLoading = false, error = m)
                }
                sendNewEffect(EditAccountClientEffect.ShowToastError(m))
            }
        )
    }

    fun updateCategory(index: Int, categoryName: String) {
        updateState {
            it.copy(
                selectedCategoryPosition = index,
                selectedCategoryName = categoryName,
            )
        }
    }

    fun updateName(clientName: String) {
        updateState {
            it.copy(
                name = clientName,
            )
        }
    }

    fun updatePhoneNumber(phoneNumber: String) {
        updateState {
            it.copy(
                phoneNumber = phoneNumber,
            )
        }
    }

    fun editClient() {
        updateState {
            it.copy(isLoadingEditClient = true)
        }
        tryToExecute(
            function = {
                manageAccountsUseCase.editAccountClient(
                    name = state.value.name,
                    mobile = state.value.phoneNumber,
                    categoryId = state.value.categories[state.value.selectedCategoryPosition].id,
                    clientId = state.value.clientId
                )
            },
            onSuccess = {
                updateState {
                    it.copy(isLoadingEditClient = false)
                }
                sendNewEffect(EditAccountClientEffect.SucceedEditClient)
            },
            onError = { _, m ->
                updateState {
                    it.copy(isLoadingEditClient = false)
                }
                sendNewEffect(EditAccountClientEffect.ShowToastError(m))
            }
        )
    }

}
