package com.alaa.alaagallo.view.my_accounts_and_sale.composable_screen.accounts

sealed interface AccountsEffect {
    data class ShowToastError(val message:String): AccountsEffect
    data object SucceedAddCategory: AccountsEffect
    data object SucceedDeleteClient: AccountsEffect
    data object SucceedDeleteCategory: AccountsEffect
    data object SucceedUpdateCategory: AccountsEffect
}