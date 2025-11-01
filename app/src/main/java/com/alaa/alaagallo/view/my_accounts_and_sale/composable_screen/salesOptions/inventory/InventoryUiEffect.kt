package com.alaa.alaagallo.view.my_accounts_and_sale.composable_screen.salesOptions.inventory

sealed interface InventoryUiEffect{
    data class ShowToastError(val message: String) : InventoryUiEffect
    data object ShowToastSuccessAddingCustomer : InventoryUiEffect
    data object ShowToastSuccessEditingCustomer : InventoryUiEffect
    data object NavigateBack : InventoryUiEffect
}