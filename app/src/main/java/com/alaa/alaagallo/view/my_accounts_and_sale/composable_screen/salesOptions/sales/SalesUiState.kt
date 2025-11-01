package com.alaa.alaagallo.view.my_accounts_and_sale.composable_screen.salesOptions.sales

import com.alaa.alaagallo.view.my_accounts_and_sale.composable_screen.salesOptions.buys.InvoiceType
import com.alaa.alaagallo.view.my_accounts_and_sale.composable_screen.salesOptions.buys.Item
import com.alaa.alaagallo.view.my_accounts_and_sale.composable_screen.salesOptions.buys.NewItemValues
import com.alaa.alaagallo.view.my_accounts_and_sale.composable_screen.salesOptions.buys.SearchInvoice
import com.alaa.domain.entity.ProductEntity
import com.alaa.domain.entity.User

data class SalesUiState(
    val isLoading: Boolean = false,
    val isSuccessSaving: Boolean = false,
    val addNewInvoiceState: AddNewInvoice = AddNewInvoice(),
    val searchInvoice: SearchInvoice = SearchInvoice(),
    val addNewItemToPreviousInvoice: Boolean = false,
    val searchValue: String = "",
    val customers: List<User> = emptyList(),
    val products: List<ProductEntity> = emptyList()
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