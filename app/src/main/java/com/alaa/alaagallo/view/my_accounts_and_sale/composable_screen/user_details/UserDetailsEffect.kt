package com.alaa.alaagallo.view.my_accounts_and_sale.composable_screen.user_details

sealed interface UserDetailsEffect {
    data class ShowToastError(val message:String): UserDetailsEffect
    data object SucceedAddOperation: UserDetailsEffect
}