package com.alaa.alaagallo.view.my_accounts_and_sale.navigation

import com.alaa.alaagallo.view.my_accounts_and_sale.composable_screen.salesOptions.reports.ReportType

sealed class Screen(
    val route: String,
) {
    data object AccountsAndSale : Screen("accountsAndSaleScreen")
    data object AllUserOptions : Screen("allUserOptions")
    data object Accounts : Screen("accountsScreen")
    data object UserDetails : Screen("userDetailsScreen")

    data object Buys : Screen("buys")
    data object Sales : Screen("sales")
    data object Suppliers : Screen("suppliers")
    data object Customers : Screen("customers")
    data object Inventory : Screen("inventory")
    data object Cashier : Screen("cashier")
    data object Damaged : Screen("damaged")
    data object BarcodePrinter : Screen("barcodePrinter")
    data object Costs : Screen("costs")
    data object Reports : Screen("reports")
    data object ReportDetails : Screen("reportDetails/{reportType}?invoiceNumber={invoiceNumber}") {
        const val REPORT_TYPE_ARG = "reportType"
        const val INVOICE_NUMBER_ARG = "invoiceNumber"

        fun createRoute(type: ReportType, invoiceNumber: String? = null): String {
            val baseRoute = "reportDetails/${type.id}"
            return "$baseRoute?invoiceNumber=${invoiceNumber.orEmpty()}"
        }
    }
    data object AddAccountClient : Screen("addAccountClientScreen")
    data object EditAccountClient : Screen("editAccountClientScreen")
    data object EditAccountInvoice : Screen("editAccountInvoiceScreen")
}
