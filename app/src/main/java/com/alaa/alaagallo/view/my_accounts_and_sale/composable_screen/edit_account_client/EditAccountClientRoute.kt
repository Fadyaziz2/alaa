package com.alaa.alaagallo.view.my_accounts_and_sale.composable_screen.edit_account_client

import androidx.lifecycle.SavedStateHandle
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.alaa.alaagallo.view.my_accounts_and_sale.navigation.Screen

fun NavGraphBuilder.editAccountClientRoute() {
    composable(
        "${Screen.EditAccountClient.route}/{${EditAccountClientArgs.CLIENT_ID}}/" +
                "{${EditAccountClientArgs.NAME}}/{${EditAccountClientArgs.PHONE_NUMBER}}/" +
                "{${EditAccountClientArgs.CATEGORY_ID}}/{${EditAccountClientArgs.CATEGORY_NAME}}",
        arguments = listOf(
            navArgument(EditAccountClientArgs.CLIENT_ID) {
                type = NavType.IntType
            },
            navArgument(EditAccountClientArgs.NAME) {
                type = NavType.StringType
            },
            navArgument(EditAccountClientArgs.PHONE_NUMBER) {
                type = NavType.StringType
            },
            navArgument(EditAccountClientArgs.CATEGORY_ID) {
                type = NavType.IntType
            },
            navArgument(EditAccountClientArgs.CATEGORY_NAME) {
                type = NavType.StringType
            }
        )
    ) {
        EditAccountClientScreen()
    }
}


class EditAccountClientArgs(savedStateHandle: SavedStateHandle) {
    val clientId: Int = checkNotNull(savedStateHandle[CLIENT_ID])
    val name: String = checkNotNull(savedStateHandle[NAME])
    val phoneNumber: String = checkNotNull(savedStateHandle[PHONE_NUMBER])
    val categoryId: Int = checkNotNull(savedStateHandle[CATEGORY_ID])
    val categoryName: String = checkNotNull(savedStateHandle[CATEGORY_NAME])

    companion object {
        const val CLIENT_ID = "clientId"
        const val NAME = "name"
        const val PHONE_NUMBER = "phoneNumber"
        const val CATEGORY_ID = "categoryId"
        const val CATEGORY_NAME = "categoryName"
    }
}