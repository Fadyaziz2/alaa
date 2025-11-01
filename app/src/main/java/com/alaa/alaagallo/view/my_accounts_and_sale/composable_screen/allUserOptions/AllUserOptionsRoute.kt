package com.alaa.alaagallo.view.my_accounts_and_sale.composable_screen.allUserOptions

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptionsBuilder
import androidx.navigation.compose.composable
import com.alaa.alaagallo.view.my_accounts_and_sale.navigation.Screen

fun NavGraphBuilder.allUserOptionsRoute() {
    composable(Screen.AllUserOptions.route) {
        AllUserOptionsScreen()
    }
}
fun NavController.navigateToAllUserOptions(builder: NavOptionsBuilder.() -> Unit = {}) {
    navigate(Screen.AllUserOptions.route, builder)
}