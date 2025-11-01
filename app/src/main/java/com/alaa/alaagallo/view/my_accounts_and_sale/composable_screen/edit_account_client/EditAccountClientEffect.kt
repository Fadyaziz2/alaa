package com.alaa.alaagallo.view.my_accounts_and_sale.composable_screen.edit_account_client

sealed interface EditAccountClientEffect {
    data class ShowToastError(val message:String): EditAccountClientEffect
    data object SucceedEditClient: EditAccountClientEffect
}