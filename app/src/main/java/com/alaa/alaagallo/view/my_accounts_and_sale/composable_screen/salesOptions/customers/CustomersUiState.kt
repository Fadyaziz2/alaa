package com.alaa.alaagallo.view.my_accounts_and_sale.composable_screen.salesOptions.customers

data class CustomersUiState(
    val isLoading: Boolean = false,
    val isDoneLoading: Boolean = false,
    val error: String = "",
    val isAddingNew: Boolean = false,
    val isEdit: Boolean = false,
    val editValues: Customer? = null,
    val searchValue: String = "",
    val name: String = "",
    val phone: String = "",
    val costs: String = "",
    val details: String = "",
    val customers: List<Customer> = emptyList(),
)

data class Customer(
    val name: String,
    val phone: String,
    val details: String,
    val costs: String,
    val id: String = ""
)