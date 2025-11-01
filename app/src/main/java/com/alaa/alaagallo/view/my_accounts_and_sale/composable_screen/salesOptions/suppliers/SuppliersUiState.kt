package com.alaa.alaagallo.view.my_accounts_and_sale.composable_screen.salesOptions.suppliers


data class SuppliersUiState(
    val isLoading: Boolean = false,
    val isDoneLoading: Boolean = false,
    val error: String = "",
    val isAddingNew: Boolean = false,
    val isEdit: Boolean = false,
    val editValues: Supplier? = null,
    val searchValue: String = "",
    val name: String = "",
    val phone: String = "",
    val costs: String = "",
    val details: String = "",
    val suppliers: List<Supplier> = emptyList(),
)

data class Supplier(
    val name: String,
    val phone: String,
    val details: String,
    val costs: String,
    val id: String = ""
)