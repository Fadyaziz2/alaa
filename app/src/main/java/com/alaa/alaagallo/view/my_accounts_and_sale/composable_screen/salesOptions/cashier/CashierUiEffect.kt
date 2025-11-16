package com.alaa.alaagallo.view.my_accounts_and_sale.composable_screen.salesOptions.cashier

sealed interface CashierUiEffect {
    data class ShowToast(val message: String) : CashierUiEffect
    data object NavigateBack : CashierUiEffect
}
