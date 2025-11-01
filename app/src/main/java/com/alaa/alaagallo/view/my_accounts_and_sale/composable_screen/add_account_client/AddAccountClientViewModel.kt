package com.alaa.alaagallo.view.my_accounts_and_sale.composable_screen.add_account_client

import com.alaa.alaagallo.view.my_accounts_and_sale.base.BaseViewModel
import com.alaa.domain.usecase.ManageAccountsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class AddAccountClientViewModel @Inject constructor(
    private val manageAccountsUseCase: ManageAccountsUseCase
) : BaseViewModel<AddAccountClientState, AddAccountClientEffect>(AddAccountClientState()) {

    init {
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
                    it.copy(isLoading = false, categories = data)
                }
            },
            onError = { _, m ->
                updateState {
                    it.copy(isLoading = false, error = m)
                }
                sendNewEffect(AddAccountClientEffect.ShowToastError(m))
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

    fun addClient() {
        updateState {
            it.copy(isLoadingAddClient = true)
        }
        tryToExecute(
            function = {
                manageAccountsUseCase.addAccountClient(
                    name = state.value.name,
                    mobile = state.value.phoneNumber,
                    categoryId = state.value.categories[state.value.selectedCategoryPosition].id
                )
            },
            onSuccess = {
                updateState {
                    it.copy(isLoadingAddClient = false)
                }
                sendNewEffect(AddAccountClientEffect.SucceedAddClient)
            },
            onError = { _, m ->
                updateState {
                    it.copy(isLoadingAddClient = false)
                }
                sendNewEffect(AddAccountClientEffect.ShowToastError(m))
            }
        )
    }

}
