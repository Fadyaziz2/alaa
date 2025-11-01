package com.alaa.data.source

import com.alaa.data.dto.AccountInvoiceDto
import com.alaa.data.dto.CategoryDto
import com.alaa.data.dto.CustomerAndResInvoicesDto
import com.alaa.data.dto.CustomerAndResourcesDto
import com.alaa.data.dto.ExpensesDto
import com.alaa.data.dto.ProductDto
import com.alaa.data.dto.ProductLimitDto
import com.alaa.data.dto.ProductReturnDto
import com.alaa.data.dto.SaleInvoiceDto
import com.alaa.data.dto.InvoiceDto
import okhttp3.RequestBody


interface IRemoteDataSource {
    suspend fun getCategoriesAndUsers(): List<CategoryDto>
    suspend fun addCategory(name: String): Boolean
    suspend fun getAccountsInvoices(clientId: Int, sort: String): AccountInvoiceDto
    suspend fun uploadOperation(data: RequestBody): Boolean

    suspend fun getAllUsers(): CustomerAndResourcesDto

    suspend fun addUser(
        name: String,
        mobile: String,
        type: String,
        note: String,
    ): String

    suspend fun updateUser(
        id: String,
        name: String,
        mobile: String,
        type: String,
        note: String,
    ): String

    suspend fun deleteUser(
        id: Int,
    ): String

    suspend fun getAllExpenseses(): List<ExpensesDto>

    suspend fun addExpenseses(
        exchangePartyName: String,
        date: String,
        amount: String,
    ): String

    suspend fun updateExpenseses(
        id: String,
        exchangePartyName: String,
        date: String,
        amount: String,
    ): String

    suspend fun deleteExpenseses(
        id: Int,
    ): String

    suspend fun getAllProduct(): List<ProductDto>
    suspend fun getAllOutOfStock(): ProductLimitDto
    suspend fun getAllProductLimit(): ProductLimitDto
    suspend fun getAllRefunds(): List<ProductReturnDto>



    suspend fun addProduct(
        name: String,
        stock: Long,
        minimumStock: Long,
        sellingPrice: Long,
        buyPrice: Long,
        status: String,
    ): String

    suspend fun updateProduct(
        id: String,
        name: String,
        stock: Long,
        minimumStock: Long,
        sellingPrice: Long,
        buyPrice: Long,
        status: String,
    ): String

    suspend fun addProductRefund(
        productId: String,
        quantity: Long,
    ): String

    suspend fun updateProductRefund(
        id: String,
        productId: String,
        quantity: Long,
    ): String

    suspend fun deleteProduct(
        id: Int,
    ): String
    suspend fun deletePerishes(
        id: Int,
    ): String

    suspend fun getAllPerishes(): List<ProductDto>

    suspend fun addPerishes(
        name: String,
        price: Long,
        date: String,
    ): String

    suspend fun updatePerishes(
        id: String,
        name: String,
        price: Long,
        date: String,
    ): String

    suspend fun getAllInvoices(): CustomerAndResInvoicesDto

    suspend fun showInvoice(id: String): SaleInvoiceDto

    suspend fun addInvoice(
        userId: String,
        type: String,
        items: List<InvoiceItem>
    ): String

    suspend fun addInvoiceItems(
        saleInvoiceId: String,
        type: String,
        items: List<InvoiceItem>
    ): String

    suspend fun updateInvoice(
        id: String,
        saleInvoiceId: String,
        productId: String,
        quantity: Long,
        sellingPrice: Long,
        buyPrice: Long,
    ): String

    suspend fun deleteInvoiceItem(
        id: String,
    ): String

    suspend fun deleteInvoice(
        id: String,
    ): String
    suspend fun updateInvoice(invoiceId: Int ,data: RequestBody): Boolean
    suspend fun addAccountClient(name: String, mobile: String, categoryId: Int): Boolean
    suspend fun editAccountClient(
        name: String,
        mobile: String,
        categoryId: Int,
        clientId: Int
    ): Boolean

    suspend fun deleteAccountClient(clientId: Int): Boolean
    suspend fun deleteCategory(categoryId: Int): Boolean
    suspend fun editCategory(categoryId: Int, name: String): Boolean
    suspend fun deleteInvoice(invoiceId: Int): Boolean
    suspend fun getInvoice(invoiceId: Int): InvoiceDto
}