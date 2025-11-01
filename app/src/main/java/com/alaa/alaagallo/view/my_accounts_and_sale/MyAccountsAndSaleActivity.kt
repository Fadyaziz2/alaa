package com.alaa.alaagallo.view.my_accounts_and_sale

import android.content.Context
import android.content.ContextWrapper
import android.content.res.Configuration
import android.content.res.Resources
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.LayoutDirection
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptionsBuilder
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.alaa.alaagallo.R
import com.alaa.alaagallo.databinding.ActivityMyAccountsAndSaleBinding
import com.alaa.alaagallo.ui.theme.AlaaTheme
import com.alaa.alaagallo.util.LocalNavigationProvider
import com.alaa.alaagallo.view.my_accounts_and_sale.navigation.AccountsNavGraph
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MyAccountsAndSaleActivity : ComponentActivity() {
    private lateinit var binding: ActivityMyAccountsAndSaleBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMyAccountsAndSaleBinding.inflate(layoutInflater)
        enableEdgeToEdge()
        actionBar?.hide()
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        binding.composeView.setContent {
            val navController = rememberNavController()
            val density = LocalDensity.current

            CompositionLocalProvider(
                LocalDensity provides Density(density = 2.5f, fontScale = 1f)
            ) {
                CompositionLocalProvider(LocalNavigationProvider provides navController) {
                    CompositionLocalProvider(LocalLayoutDirection provides LayoutDirection.Rtl) {
                        AlaaTheme {
                            AccountsNavGraph()
                        }
                    }
                }
            }
        }
    }
}


//fun NavGraphBuilder.splashRoute(){
//    composable(Screen.Splash.route){
//        SplashScreen()
//    }
//}
//
//fun NavController.navigateToLogin(
//    builder: NavOptionsBuilder.() -> Unit = {},
//) {
//    navigate(Screen.Login.route, builder = builder)
//}
//fun NavController.navigateToMembership(
//    builder: NavOptionsBuilder.() -> Unit = {},
//) {
//    navigate(Screen.Membership.route, builder = builder)
//}