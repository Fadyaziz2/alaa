package com.alaa.alaagallo.view.my_accounts_and_sale.composable_screen.user_details

import androidx.lifecycle.SavedStateHandle
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptionsBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.alaa.alaagallo.view.my_accounts_and_sale.navigation.Screen

fun NavGraphBuilder.userDetailsRoute() {
    composable("${Screen.UserDetails.route}/{${UserDetailsArgs.CLIENT_ID}}/{${UserDetailsArgs.PHONE_NUMBER}}/{${UserDetailsArgs.NAME}}",
        arguments = listOf(
            navArgument(UserDetailsArgs.CLIENT_ID) {
                type = NavType.IntType
            }
            , navArgument(UserDetailsArgs.PHONE_NUMBER) {
                type = NavType.StringType
            }
            , navArgument(UserDetailsArgs.NAME) {
                type = NavType.StringType
            }
        )) {
        UserDetailsScreen()
    }
}
fun NavController.navigateToEditAccountClient(
    clientId: Int,
    name: String,
    phone: String,
    categoryId: Int,
    categoryName: String,
    builder: NavOptionsBuilder.() -> Unit = {}
) {
    navigate("${Screen.EditAccountClient.route}/$clientId/$name/$phone/$categoryId/$categoryName", builder)
}
class UserDetailsArgs(savedStateHandle: SavedStateHandle) {
    val clientId: Int = checkNotNull(savedStateHandle[CLIENT_ID])
    val phoneNumber: String = checkNotNull(savedStateHandle[PHONE_NUMBER])
    val name: String = checkNotNull(savedStateHandle[NAME])

    companion object {
        const val CLIENT_ID = "clientId"
        const val PHONE_NUMBER = "phoneNumber"
        const val NAME = "name"
    }
}