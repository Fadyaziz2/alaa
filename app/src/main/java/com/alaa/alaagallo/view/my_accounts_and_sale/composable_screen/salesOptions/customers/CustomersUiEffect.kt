package com.alaa.alaagallo.view.my_accounts_and_sale.composable_screen.salesOptions.customers

import com.alaa.alaagallo.view.my_accounts_and_sale.composable_screen.accounts.AccountsEffect


sealed interface CustomersUiEffect {
    data class ShowToastError(val message: String) : CustomersUiEffect
    data object ShowToastSuccessAddingCustomer : CustomersUiEffect
    data object ShowToastSuccessEditingCustomer : CustomersUiEffect
    data object NavigateBack : CustomersUiEffect
}

