package com.alaa.alaagallo.view.my_accounts_and_sale.composable_screen.salesOptions.sales

sealed interface SalesUiEffect{
    data object NavigateBack : SalesUiEffect
    data object ShowToastSuccessAddingNewItemToNewInvoice : SalesUiEffect
    data object ShowToastSuccessAddingNewInvoice : SalesUiEffect
    data class ShowToastError(val message: String) : SalesUiEffect
}