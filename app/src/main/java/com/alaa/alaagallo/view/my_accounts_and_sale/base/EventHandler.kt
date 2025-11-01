package com.alaa.alaagallo.view.my_accounts_and_sale.base

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collectLatest

@Composable
fun <T> EventHandler(
    effects: Flow<T>,
    handleEffect: (T) -> Unit,
) {
    LaunchedEffect(key1 = Unit) {
        effects.collectLatest { effect ->
            handleEffect(effect)
        }
    }
}