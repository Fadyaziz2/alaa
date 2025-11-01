package com.alaa.alaagallo.view.my_accounts_and_sale.composable_screen.salesOptions.suppliers


sealed interface SuppliersUiEffect {

    data class ShowToastError(val message: String) : SuppliersUiEffect
    data object ShowToastSuccessAddingSupplier : SuppliersUiEffect
    data object ShowToastSuccessEditingSupplier : SuppliersUiEffect
    data object NavigateBack : SuppliersUiEffect

}
