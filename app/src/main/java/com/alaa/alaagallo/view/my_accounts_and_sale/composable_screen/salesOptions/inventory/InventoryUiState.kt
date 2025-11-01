package com.alaa.alaagallo.view.my_accounts_and_sale.composable_screen.salesOptions.inventory

import com.alaa.alaagallo.view.my_accounts_and_sale.composable_screen.salesOptions.buys.NewItemValues
import com.alaa.alaagallo.view.my_accounts_and_sale.composable_screen.salesOptions.customers.Customer
import com.alaa.domain.entity.ProductEntity
import com.alaa.domain.entity.ProductReturnEntity

data class InventoryUiState(
    val isLoading: Boolean = false,
    val isDoneLoading: Boolean = false,
    val error: String = "",
    val isAddingNew: Boolean = false,
    val isAddingReturn: Boolean = false,
    val isEdit: Boolean = false,
    val returnItemId: Int? = null,
    val editValues: ProductEntity? = null,
    val searchValue: String = "",
    val selectedInventoryItem : MenuItems = MenuItems.INVENTORY,

    val newItemValues: NewItemValues = NewItemValues(),
    val productItemsTypes: ProductItemsTypes = ProductItemsTypes(),
)

data class ProductItemsTypes(
    val inventoryItems: List<ProductEntity> = emptyList(),
    val returnItems: List<ProductReturnEntity> = emptyList(),
    val soldOutItems: List<ProductEntity> = emptyList(),
    val orderLimitItem: List<ProductEntity> = emptyList(),
)