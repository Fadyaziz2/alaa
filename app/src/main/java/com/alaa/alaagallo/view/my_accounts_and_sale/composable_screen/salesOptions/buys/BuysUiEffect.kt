package com.alaa.alaagallo.view.my_accounts_and_sale.composable_screen.salesOptions.buys


sealed interface BuysUiEffect {
    data object NavigateBack : BuysUiEffect
    data object ShowToastSuccessAddingNewItemToNewInvoice : BuysUiEffect
    data object ShowToastSuccessAddingNewInvoice : BuysUiEffect
    data class ShowToastError(val message: String) : BuysUiEffect

}