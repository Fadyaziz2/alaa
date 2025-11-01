package com.alaa.alaagallo.view.my_accounts_and_sale.composable_screen.accounts

import com.alaa.alaagallo.view.my_accounts_and_sale.base.BaseViewModel
import com.alaa.domain.usecase.ManageAccountsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class AccountsViewModel @Inject constructor(
    private val manageAccountsUseCase: ManageAccountsUseCase
) : BaseViewModel<AccountsState, AccountsEffect>(AccountsState()) {
    fun getCategories() {
        updateState {
            it.copy(isLoading = true)
        }
        tryToExecute(
            function = {
                manageAccountsUseCase.getCategoriesAndUsers()
            },
            onSuccess = { data ->
                var totalR = 0
                var totalD = 0
                data.forEach {
                    totalR += it.totalReceivables
                    totalD += it.totalDebt
                }
                updateState {
                    it.copy(
                        isLoading = false, categories = data,
                        categoriesUi = data.map { item ->
                            CategoryUi(
                                id = item.id,
                                name = item.name,
                                numOfAccounts = item.accountCustomers.size,
                                isSelected = item.name == state.value.selectedCategoryName
                            )
                        },
                        allUsers = data.flatMap { category ->
                            category.accountCustomers.map { accountCustomer ->
                                accountCustomer.toUser(category.name, category.id)
                            }
                        },
                        totalShowingReceivables = totalR.toString(),
                        totalShowingDept = totalD.toString(),
                    )
                }
                if (state.value.isFirst)
                    updateState {
                        it.copy(
                            users = data.flatMap { category ->
                                category.accountCustomers.map { accountCustomer ->
                                    accountCustomer.toUser(category.name, category.id)
                                }
                            }
                        )
                    }
                if (!state.value.isFirst && state.value.selectedCategoryName != "الكل")
                    onClickCategory(state.value.selectedCategoryPos)

                updateState {
                    it.copy(
                        isFirst = false
                    )
                }
            },
            onError = { _, m ->
                updateState {
                    it.copy(isLoading = false, error = m)
                }
                sendNewEffect(AccountsEffect.ShowToastError(m))
            }
        )
    }

    fun onClickCategory(index: Int) {
        updateState {
            it.copy(
                selectedCategoryPos = index,
                selectedCategoryName = state.value.categoriesUi[index].name,
                isSelectedAll = false,
                categoriesUi = state.value.categoriesUi.mapIndexed { pos, item ->
                    if (pos == index)
                        item.copy(isSelected = true)
                    else
                        item.copy(isSelected = false)
                },

                totalShowingReceivables = state.value.categories[index].totalReceivables.toString(),
                totalShowingDept = state.value.categories[index].totalDebt.toString(),
                users = state.value.categories[index].accountCustomers.map { accountCustomer ->
                    accountCustomer.toUser(
                        state.value.categories[index].name,
                        state.value.categories[index].id
                    )
                }
            )
        }
    }

    fun onClickAllItem() {
        updateState {
            it.copy(
                selectedCategoryName = "الكل",
                categoriesUi = state.value.categoriesUi.mapIndexed { _, item ->
                    item.copy(isSelected = false)
                },
                totalShowingReceivables = state.value.totalReceivables,
                totalShowingDept = state.value.totalDept,
                users = state.value.allUsers,
                isSelectedAll = true
            )
        }
    }

    fun resetAddCategoryStatus() {
        updateState {
            it.copy(
                isSucceedAddCategory = false
            )
        }
    }

    fun addCategory(name: String) {
        tryToExecute(
            function = {
                updateState {
                    it.copy(isLoadingAddCategory = true)
                }
                manageAccountsUseCase.addCategory(name)
            },
            onError = { _, m ->
                updateState {
                    it.copy(isLoadingAddCategory = false)
                }
                sendNewEffect(AccountsEffect.ShowToastError(m))
            },
            onSuccess = { data ->
                updateState {
                    it.copy(isLoadingAddCategory = false, isSucceedAddCategory = data)
                }
                sendNewEffect(AccountsEffect.SucceedAddCategory)
                getCategories()
            }
        )
    }

    fun deleteAccountClient(clientId: Int) {
        tryToExecute(
            function = {
                updateState {
                    it.copy(isLoadingDeleteClient = true)
                }
                manageAccountsUseCase.deleteAccountClient(clientId)
            },
            onError = { _, m ->
                updateState {
                    it.copy(isLoadingDeleteClient = false)
                }
                sendNewEffect(AccountsEffect.ShowToastError(m))
            },
            onSuccess = { data ->
                if (data) {
                    updateState {
                        it.copy(isLoadingDeleteClient = false)
                    }
                    sendNewEffect(AccountsEffect.SucceedDeleteClient)
                    getCategories()
                }
            }
        )
    }

    fun deleteCategory(categoryId: Int) {
        tryToExecute(
            function = {
                updateState {
                    it.copy(isLoadingDeleteCategory = true)
                }
                manageAccountsUseCase.deleteCategory(categoryId)
            },
            onError = { _, m ->
                updateState {
                    it.copy(isLoadingDeleteCategory = false)
                }
                sendNewEffect(AccountsEffect.ShowToastError(m))
            },
            onSuccess = { data ->
                if (data) {
                    updateState {
                        it.copy(isLoadingDeleteCategory = false)
                    }
                    sendNewEffect(AccountsEffect.SucceedDeleteCategory)
                    getCategories()
                }
            }
        )
    }

    fun editCategory(categoryId: Int, newName: String) {
        tryToExecute(
            function = {
                updateState {
                    it.copy(isLoadingUpdateCategory = true)
                }
                manageAccountsUseCase.editCategory(categoryId, newName)
            },
            onError = { _, m ->
                updateState {
                    it.copy(isLoadingUpdateCategory = false)
                }
                sendNewEffect(AccountsEffect.ShowToastError(m))
            },
            onSuccess = { data ->
                if (data) {
                    updateState {
                        it.copy(isLoadingUpdateCategory = false)
                    }
                    sendNewEffect(AccountsEffect.SucceedUpdateCategory)
                    getCategories()
                }
            }
        )
    }
}