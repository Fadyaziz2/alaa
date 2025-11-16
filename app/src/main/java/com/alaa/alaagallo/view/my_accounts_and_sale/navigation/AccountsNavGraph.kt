package com.alaa.alaagallo.view.my_accounts_and_sale.navigation


import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.alaa.alaagallo.util.LocalNavigationProvider
import com.alaa.alaagallo.view.my_accounts_and_sale.composable_screen.accounts.accountsRoute
import com.alaa.alaagallo.view.my_accounts_and_sale.composable_screen.add_account_client.addAccountClientRoute
import com.alaa.alaagallo.view.my_accounts_and_sale.composable_screen.edit_account_client.editAccountClientRoute
import com.alaa.alaagallo.view.my_accounts_and_sale.composable_screen.edit_account_invoice.editAccountInvoiceRoute
import com.alaa.alaagallo.view.my_accounts_and_sale.composable_screen.allUserOptions.AllUserOptionsScreen
import com.alaa.alaagallo.view.my_accounts_and_sale.composable_screen.allUserOptions.allUserOptionsRoute
import com.alaa.alaagallo.view.my_accounts_and_sale.composable_screen.salesOptions.buys.BuysScreen
import com.alaa.alaagallo.view.my_accounts_and_sale.composable_screen.salesOptions.cashier.CashierScreen
import com.alaa.alaagallo.view.my_accounts_and_sale.composable_screen.salesOptions.costs.CostScreen
import com.alaa.alaagallo.view.my_accounts_and_sale.composable_screen.salesOptions.customers.CustomersScreen
import com.alaa.alaagallo.view.my_accounts_and_sale.composable_screen.salesOptions.damaged.DamagedScreen
import com.alaa.alaagallo.view.my_accounts_and_sale.composable_screen.salesOptions.inventory.InventoryScreen
import com.alaa.alaagallo.view.my_accounts_and_sale.composable_screen.salesOptions.sales.SalesScreen
import com.alaa.alaagallo.view.my_accounts_and_sale.composable_screen.salesOptions.suppliers.SuppliersScreen
import com.alaa.alaagallo.view.my_accounts_and_sale.composable_screen.user_details.userDetailsRoute

@Composable
fun AccountsNavGraph() {
    val navController = LocalNavigationProvider.current
    NavHost(
        navController = navController,
        startDestination = Graph.HOME,
    ) {
        accountsGraph()
        accountsRoute()
        allUserOptionsRoute()
        userDetailsRoute()

        composable(Screen.Buys.route) {
            BuysScreen()
        }

        composable(Screen.Sales.route) {
            SalesScreen()
        }
        composable(Screen.Inventory.route) {
            InventoryScreen()
        }

        composable(Screen.Cashier.route) {
            CashierScreen()
        }

        composable(Screen.Suppliers.route) {
            SuppliersScreen()
        }

        composable(Screen.Customers.route) {
            CustomersScreen()
        }

        composable(Screen.Damaged.route) {
            DamagedScreen()
        }
        composable(Screen.Costs.route) {
            CostScreen()
        }
        addAccountClientRoute()
        editAccountClientRoute()
        editAccountInvoiceRoute()
    }
}