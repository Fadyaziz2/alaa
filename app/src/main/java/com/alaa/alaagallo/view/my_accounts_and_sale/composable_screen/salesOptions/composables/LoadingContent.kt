package com.alaa.alaagallo.view.my_accounts_and_sale.composable_screen.salesOptions.composables

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.alaa.alaagallo.view.my_accounts_and_sale.composable.LoadingIndicator

@Composable
fun LoadingContent(isLoading: Boolean, content: @Composable () -> Unit) {
    if (isLoading) {
        Box(
            Modifier.fillMaxSize(), contentAlignment = Alignment.Center
        ) {
            LoadingIndicator()
        }
    } else {
        content()
    }
}