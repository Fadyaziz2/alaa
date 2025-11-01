package com.alaa.alaagallo.view.my_accounts_and_sale.composable_screen.salesOptions.costs


data class CostUiState(
    val isLoading: Boolean = false,
    val isDoneLoading: Boolean = false,
    val error: String = "",
    val isAddingNew: Boolean = false,
    val isEdit: Boolean = false,
    val editValues: Cost? = null,
    val searchValue: String = "",
    val name: String = "",
    val date: String = "",
    val amount: String = "",
    val costs: List<Cost> = emptyList(),
)

data class Cost(
    val name: String,
    val date: String,
    val amount: String,
    val id: String = ""
)