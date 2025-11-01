package com.alaa.alaagallo.view.my_accounts_and_sale.composable_screen.add_account_client

sealed interface AddAccountClientEffect {
    data class ShowToastError(val message:String): AddAccountClientEffect
    data object SucceedAddClient: AddAccountClientEffect
}