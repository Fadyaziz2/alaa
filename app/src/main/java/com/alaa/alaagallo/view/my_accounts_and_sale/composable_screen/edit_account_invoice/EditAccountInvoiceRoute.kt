package com.alaa.alaagallo.view.my_accounts_and_sale.composable_screen.edit_account_invoice

import androidx.lifecycle.SavedStateHandle
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptionsBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.alaa.alaagallo.view.my_accounts_and_sale.navigation.Screen

fun NavGraphBuilder.editAccountInvoiceRoute() {
    composable("${Screen.EditAccountInvoice.route}/{${EditAccountInvoiceArgs.INVOICE_ID}}",
        arguments = listOf(
            navArgument(EditAccountInvoiceArgs.INVOICE_ID) {
                type = NavType.IntType
            }

        )) {
        EditAccountInvoiceScreen()
    }
}
fun NavController.navigateToEditAccountInvoice(
    invoiceId: Int,
    builder: NavOptionsBuilder.() -> Unit = {}
) {
    navigate("${Screen.EditAccountInvoice.route}/$invoiceId", builder)
}
class EditAccountInvoiceArgs(savedStateHandle: SavedStateHandle) {
    val invoiceId: Int = checkNotNull(savedStateHandle[INVOICE_ID])

    companion object {
        const val INVOICE_ID = "invoiceId"
    }
}