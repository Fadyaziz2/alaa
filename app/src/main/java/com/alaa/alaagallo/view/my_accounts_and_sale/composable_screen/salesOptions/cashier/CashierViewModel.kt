package com.alaa.alaagallo.view.my_accounts_and_sale.composable_screen.salesOptions.cashier

import com.alaa.alaagallo.view.my_accounts_and_sale.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class CashierViewModel @Inject constructor() :
    BaseViewModel<CashierUiState, CashierUiEffect>(CashierUiState()) {

    fun onClickAddCashier() {
        updateState {
            it.copy(
                isFormVisible = true,
                isEdit = false,
                formState = CashierFormState()
            )
        }
    }

    fun onEditCashier(cashier: CashierItem) {
        updateState {
            it.copy(
                isFormVisible = true,
                isEdit = true,
                formState = CashierFormState(
                    id = cashier.id,
                    name = cashier.name,
                    phone = cashier.phone,
                    password = cashier.password
                )
            )
        }
    }

    fun onDeleteCashier(cashier: CashierItem) {
        updateState {
            it.copy(
                cashiers = it.cashiers.filterNot { item -> item.id == cashier.id }
            )
        }
        sendNewEffect(CashierUiEffect.ShowToast("تم حذف الكاشير"))
    }

    fun onChangeName(value: String) {
        updateState { it.copy(formState = it.formState.copy(name = value)) }
    }

    fun onChangePhone(value: String) {
        updateState { it.copy(formState = it.formState.copy(phone = value)) }
    }

    fun onChangePassword(value: String) {
        updateState { it.copy(formState = it.formState.copy(password = value)) }
    }

    fun onSubmitForm() {
        val currentState = state.value
        val form = currentState.formState
        if (form.name.isBlank() || form.phone.isBlank() || form.password.isBlank()) {
            sendNewEffect(CashierUiEffect.ShowToast("برجاء ادخال جميع الحقول"))
            return
        }

        if (currentState.isEdit && form.id != null) {
            val updated = currentState.cashiers.map {
                if (it.id == form.id) {
                    it.copy(
                        name = form.name,
                        phone = form.phone,
                        password = form.password
                    )
                } else it
            }
            updateState {
                it.copy(
                    cashiers = updated,
                    isFormVisible = false,
                    isEdit = false,
                    formState = CashierFormState()
                )
            }
            sendNewEffect(CashierUiEffect.ShowToast("تم حفظ التعديل بنجاح"))
        } else {
            val nextId = (currentState.cashiers.maxOfOrNull { it.id } ?: 0) + 1
            val newCashier = CashierItem(
                id = nextId,
                name = form.name,
                phone = form.phone,
                password = form.password
            )
            updateState {
                it.copy(
                    cashiers = it.cashiers + newCashier,
                    isFormVisible = false,
                    isEdit = false,
                    formState = CashierFormState()
                )
            }
            sendNewEffect(CashierUiEffect.ShowToast("تم اضافة الكاشير بنجاح"))
        }
    }

    fun navigateBackToPrevious() {
        if (state.value.isFormVisible) {
            updateState {
                it.copy(
                    isFormVisible = false,
                    isEdit = false,
                    formState = CashierFormState()
                )
            }
        } else {
            sendNewEffect(CashierUiEffect.NavigateBack)
        }
    }
}
