package com.alaa.alaagallo.view.my_accounts_and_sale.composable_screen.salesOptions.damaged

import com.alaa.domain.entity.ProductEntity

data class DamagedUiState(
    val isLoading: Boolean = false,
    val isDoneLoading: Boolean = false,
    val error: String = "",
    val isAddingNew: Boolean = false,
    val isEdit: Boolean = false,
    val editValues: ProductEntity? = null,
    val searchValue: String = "",
    val name: String = "",
    val date: String = "",
    val price: String = "",
    val damaged: List<ProductEntity> = emptyList(),
)