package com.alaa.alaagallo.view.my_accounts_and_sale.composable

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import com.alaa.alaagallo.ui.theme.Theme

@Composable
fun TableTextItem(modifier: Modifier = Modifier, text: String) {
    Text(
        text, style = Theme.typography.tableContent, modifier = modifier,
        textAlign = TextAlign.Center,
        overflow = TextOverflow.Ellipsis,
        color = Color.Black
    )
}