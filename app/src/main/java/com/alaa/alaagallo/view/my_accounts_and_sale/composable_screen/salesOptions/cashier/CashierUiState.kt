package com.alaa.alaagallo.view.my_accounts_and_sale.composable_screen.salesOptions.cashier

import androidx.compose.runtime.Immutable

@Immutable
data class CashierUiState(
    val cashiers: List<CashierItem> = emptyList(),
    val isFormVisible: Boolean = false,
    val isEdit: Boolean = false,
    val formState: CashierFormState = CashierFormState(),
    val error: String? = null,
)

@Immutable
data class CashierItem(
    val id: Int,
    val name: String,
    val phone: String,
    val password: String,
)

@Immutable
data class CashierFormState(
    val id: Int? = null,
    val name: String = "",
    val phone: String = "",
    val password: String = "",
)
