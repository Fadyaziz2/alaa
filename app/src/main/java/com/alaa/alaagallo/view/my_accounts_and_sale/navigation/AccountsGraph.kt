package com.alaa.alaagallo.view.my_accounts_and_sale.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.navigation
import com.alaa.alaagallo.view.my_accounts_and_sale.composable_screen.allUserOptions.allUserOptionsRoute
import com.alaa.alaagallo.view.my_accounts_and_sale.composable_screen.my_accounts_and_sale.myAccountsAndSaleRoute

fun NavGraphBuilder.accountsGraph() {
    navigation(
        startDestination = Screen.AccountsAndSale.route,
        route = Graph.HOME
    ){
        myAccountsAndSaleRoute()
    }
}