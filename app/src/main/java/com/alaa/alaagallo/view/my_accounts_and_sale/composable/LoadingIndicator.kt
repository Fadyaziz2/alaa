package com.alaa.alaagallo.view.my_accounts_and_sale.composable

import androidx.compose.foundation.layout.size
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.alaa.alaagallo.ui.theme.Theme


@Composable
fun LoadingIndicator(color: Color=Theme.colors.orange) {
    CircularProgressIndicator(
        modifier = Modifier.size(20.dp),
        strokeWidth = 4.dp,
        color = color
    )
}