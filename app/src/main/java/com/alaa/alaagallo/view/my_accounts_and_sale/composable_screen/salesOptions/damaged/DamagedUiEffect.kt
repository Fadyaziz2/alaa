package com.alaa.alaagallo.view.my_accounts_and_sale.composable_screen.salesOptions.damaged

sealed interface DamagedUiEffect{
    data class ShowToastError(val message: String) : DamagedUiEffect
    data object ShowToastSuccessAddingDamaged : DamagedUiEffect
    data object ShowToastSuccessEditingDamaged : DamagedUiEffect
    data object NavigateBack : DamagedUiEffect
}
