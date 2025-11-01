package com.alaa.alaagallo.view.my_accounts_and_sale.composable_screen.accounts

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptionsBuilder
import androidx.navigation.compose.composable
import com.alaa.alaagallo.view.my_accounts_and_sale.navigation.Screen

fun NavGraphBuilder.accountsRoute() {
    composable(Screen.Accounts.route) {
        AccountsScreen()
    }
}

fun NavController.navigateToUserDetails(
    clientId: Int,
    phone: String,
    name: String,
    builder: NavOptionsBuilder.() -> Unit = {}
) {
    navigate("${Screen.UserDetails.route}/$clientId/$phone/$name", builder)
}

fun NavController.navigateToAddAccountClient(
    builder: NavOptionsBuilder.() -> Unit = {}
) {
    navigate(Screen.AddAccountClient.route, builder)
}
