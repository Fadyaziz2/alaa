package com.alaa.alaagallo.view.my_accounts_and_sale.composable_screen.allUserOptions

import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.alaa.alaagallo.R
import com.alaa.alaagallo.ui.theme.Theme
import com.alaa.alaagallo.util.LocalNavigationProvider
import com.alaa.alaagallo.view.my_accounts_and_sale.composable_screen.salesOptions.composables.SalesScaffold
import com.alaa.alaagallo.view.my_accounts_and_sale.navigation.Screen

@Composable
fun AllUserOptionsScreen() {
    AllUserOptionsContent()
}

@Composable
fun AllUserOptionsContent() {
    val navController = LocalNavigationProvider.current

    SalesScaffold { padding ->
        CompositionLocalProvider(LocalLayoutDirection provides LayoutDirection.Ltr) {
            LazyVerticalGrid(
                modifier = Modifier.padding(padding),
                columns = GridCells.Fixed(2),
                verticalArrangement = Arrangement.spacedBy(8.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                contentPadding = PaddingValues(8.dp)
            ) {
                items(Options.entries) { option ->
                    Column(
                        modifier = Modifier
                            .size(150.dp)
                            .border(1.dp, Color(0xffFFC801), RoundedCornerShape(8.dp))
                            .clip(RoundedCornerShape(8.dp))
                            .clickable {
                                navController.navigate(option.route)
                            },
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center
                    ) {
                        Image(
                            painter = painterResource(option.image),
                            modifier = Modifier.size(60.dp),
                            contentDescription = ""
                        )
                        Spacer(Modifier.height(4.dp))

                        Text(
                            option.title,
                            style = Theme.typography.footerAddOption.copy(
                                fontSize = 16.sp,
                                fontWeight = FontWeight.W600
                            )
                        )

                    }
                }
            }
        }
    }
}


private enum class Options(val title: String, @DrawableRes val image: Int, val route: String) {
    Buys(
        title = "مشتريات",
        image = R.drawable.buys,
        route = Screen.Buys.route
    ),
    Sales(
        title = "مبيعات",
        image = R.drawable.sales,
        route = Screen.Sales.route
    ),
    Suppliers(
        title = "موردين",
        image = R.drawable.supplers,
        route = Screen.Suppliers.route
    ),
    Customers(
        title = "عملاء",
        image = R.drawable.customers,
        route = Screen.Customers.route
    ),
    Inventory(
        title = "المخزن",
        image = R.drawable.inventory,
        route = Screen.Inventory.route
    ),
    Cashier(
        title = "الكاشير",
        image = R.drawable.cashier_icon,
        route = Screen.Cashier.route
    ),
    Damaged(
        title = "هوالك",
        image = R.drawable.dameged,
        route = Screen.Damaged.route
    ),
    Costs(title = "مصروفات", image = R.drawable.costs, route = Screen.Costs.route),
    Reports(title = "التقارير", image = R.drawable.reports_icon, route = Screen.Reports.route),
}