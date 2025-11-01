package com.alaa.alaagallo.view.my_accounts_and_sale.navigation


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
    data object Damaged : Screen("damaged")
    data object Costs : Screen("costs")
    data object AddAccountClient : Screen("addAccountClientScreen")
    data object EditAccountClient : Screen("editAccountClientScreen")
    data object EditAccountInvoice : Screen("editAccountInvoiceScreen")
}
