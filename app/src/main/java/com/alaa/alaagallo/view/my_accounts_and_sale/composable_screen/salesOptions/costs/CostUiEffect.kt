package com.alaa.alaagallo.view.my_accounts_and_sale.composable_screen.salesOptions.costs


sealed interface CostUiEffect{
    data class ShowToastError(val message: String) : CostUiEffect
    data object ShowToastSuccessAddingCost : CostUiEffect
    data object ShowToastSuccessEditingCost : CostUiEffect
    data object NavigateBack : CostUiEffect
}

