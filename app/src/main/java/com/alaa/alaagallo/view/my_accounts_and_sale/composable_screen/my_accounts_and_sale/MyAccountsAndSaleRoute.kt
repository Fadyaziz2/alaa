package com.alaa.alaagallo.view.my_accounts_and_sale.composable_screen.my_accounts_and_sale

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptionsBuilder
import androidx.navigation.compose.composable
import com.alaa.alaagallo.view.my_accounts_and_sale.navigation.Screen

fun NavGraphBuilder.myAccountsAndSaleRoute() {
    composable(Screen.AccountsAndSale.route) {
        MyAccountsAndSaleScreen()
    }
}
fun NavController.navigateToAccounts(builder: NavOptionsBuilder.() -> Unit = {}) {
    navigate(Screen.Accounts.route, builder)
}
