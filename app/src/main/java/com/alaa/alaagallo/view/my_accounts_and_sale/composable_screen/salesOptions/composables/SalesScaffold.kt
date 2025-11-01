package com.alaa.alaagallo.view.my_accounts_and_sale.composable_screen.salesOptions.composables

import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.alaa.alaagallo.R
import com.alaa.alaagallo.ui.theme.Theme
import com.alaa.alaagallo.util.LocalNavigationProvider

@Composable
fun SalesScaffold(
    title: String = "",
    navBack: (() -> Unit)? = null,
    actions: @Composable RowScope.() -> Unit = {},
    content: @Composable (PaddingValues) -> Unit = {}
) {
    val navController = LocalNavigationProvider.current
    Scaffold(
        topBar = {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier
                    .background(Color.Black)
                    .height(60.dp)
                    .fillMaxWidth()
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    IconButton(onClick = {
                        if (navBack == null)
                            navController.popBackStack()
                        else
                            navBack()
                    }) {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_back),
                            contentDescription = "",
                            tint = Color.White,
                            modifier = Modifier
                        )
                    }
                    Spacer(Modifier.width(20.dp))
                    Text(
                        title,
                        style = Theme.typography.headerMainTitle,
                        color = Color.White
                    )
                }
                Row(verticalAlignment = Alignment.CenterVertically) {
                    actions()
                }
            }
        }
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.White)
        ) {
            content(paddingValues)
        }
    }
}