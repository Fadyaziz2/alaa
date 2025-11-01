package com.alaa.alaagallo.view.my_accounts_and_sale.composable_screen.salesOptions.buys

import com.alaa.domain.entity.ProductEntity
import com.alaa.domain.entity.User

data class BuysUiState(
    val isLoading: Boolean = false,
    val isSuccessSaving: Boolean = false,
    val addNewInvoiceState: AddNewInvoice = AddNewInvoice(),
    val searchInvoice: SearchInvoice = SearchInvoice(),
    val addNewItemToPreviousInvoice: Boolean = false,
    val searchValue: String = "",
    val customers: List<User> = emptyList(),
    val products: List<ProductEntity> = emptyList()
)

data class SearchInvoice(
    val visible: Boolean = false,
    val invoices: List<BuyInvoice> = emptyList(),
    val selectedInvoice: BuyInvoice? = null,
)

data class AddNewInvoice(
    val visible: Boolean = false,
    val addNewItemVisible: Boolean = false,
    val isSaveLoading: Boolean = false,
    val items: List<Item> = emptyList(),
    val customer: User? = null,
    val type: InvoiceType = InvoiceType.Amount,
    val newInvoiceValues: NewItemValues = NewItemValues(),
)

data class NewItemValues(
    val id: String = "",
    val code: String = "",
    val name: String = "",
    val qty: String = "",
    val priceForBuy: String = "",
    val priceForSale: String = "",
    val orderLimit: String = "",
    val isDoneLoading: Boolean = false
)

data class BuyInvoice(
    val id: Int,
    val supplierName: String,
    val total: Double,
    val qty: Double,
    val dateTime: String,
    val type: InvoiceType,
    val items: List<Item> = emptyList()
)

data class Item(
    val id: Int,
    val code: String,
    val name: String,
    val qty: Double,
    val price: Double,
    val total: Double,
    val priceForBuy: Double = 0.0,
    val priceForSell: Double = 0.0
)

enum class InvoiceType {
    Amount,
    Later
}