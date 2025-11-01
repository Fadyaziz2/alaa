package com.alaa.alaagallo.view.my_accounts_and_sale.composable_screen.my_accounts_and_sale

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.alaa.alaagallo.R
import com.alaa.alaagallo.util.LocalNavigationProvider
import com.alaa.alaagallo.view.my_accounts_and_sale.composable.MyAccountsAndSaleItem
import com.alaa.alaagallo.view.my_accounts_and_sale.composable_screen.allUserOptions.navigateToAllUserOptions

@Composable
fun MyAccountsAndSaleScreen() {
    MyAccountsAndSaleContent()
}

@Composable
private fun MyAccountsAndSaleContent() {
    val navController = LocalNavigationProvider.current
    Column(
        modifier = Modifier.fillMaxSize().background(Color.White).padding(32.dp),
        verticalArrangement = Arrangement.SpaceEvenly,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        MyAccountsAndSaleItem(
            title = stringResource(R.string.accounts),
            icon = R.drawable.ic_accounts
        ) {
            navController.navigateToAccounts()
        }
        MyAccountsAndSaleItem(title = stringResource(R.string.sales), icon = R.drawable.ic_sales) {
            navController.navigateToAllUserOptions()
        }
    }
}