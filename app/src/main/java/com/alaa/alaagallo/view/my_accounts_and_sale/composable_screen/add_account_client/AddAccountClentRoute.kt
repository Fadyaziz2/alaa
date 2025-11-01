package com.alaa.alaagallo.view.my_accounts_and_sale.composable_screen.add_account_client

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.alaa.alaagallo.view.my_accounts_and_sale.navigation.Screen

fun NavGraphBuilder.addAccountClientRoute() {
    composable(Screen.AddAccountClient.route) {
        AddAccountClientScreen()
    }
}
