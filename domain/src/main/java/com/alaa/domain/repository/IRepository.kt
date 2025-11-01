package com.alaa.domain.repository

import com.alaa.domain.entity.AccountInvoice
import com.alaa.domain.entity.Category
import com.alaa.domain.entity.CustomerAndResInvoicesEntity
import com.alaa.domain.entity.CustomerAndResources
import com.alaa.domain.entity.ExpensesEntity
import com.alaa.domain.entity.ProductEntity
import com.alaa.domain.entity.ProductLimitEntity
import com.alaa.domain.entity.ProductReturnEntity
import com.alaa.domain.entity.SaleInvoiceEntity
import com.alaa.domain.entity.Invoice
import java.io.File


interface IRepository {
    suspend fun getCategoriesAndUsers(): List<Category>
    suspend fun addCategory(name: String): Boolean
    suspend fun getAccountsInvoices(clientId: Int, sort: String): AccountInvoice
    suspend fun uploadOperation(
        customerId: Int,
        amount: String,
        dateTime: String,
        type: String,
        additionalNote: String,
        image: File
    ): Boolean

    suspend fun updateInvoice(
        invoiceId: Int,
        accountCustomerId: Int,
        date: String,
        amount: String,
        type: String,
        additionalNote: String,
        image: File?
    ): Boolean

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
    suspend fun getInvoice(invoiceId: Int): Invoice

    suspend fun getAllUsers(): CustomerAndResources

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

    suspend fun getAllExpenseses(): List<ExpensesEntity>

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

    suspend fun getAllProduct(): List<ProductEntity>
    suspend fun getAllOutOfStock(): ProductLimitEntity
    suspend fun getAllProductLimit(): ProductLimitEntity
    suspend fun getAllRefunds(): List<ProductReturnEntity>

    suspend fun addProductRefund(
        productId: String,
        quantity: Long,
    ): String

    suspend fun updateProductRefund(
        id: String,
        productId: String,
        quantity: Long,
    ): String

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

    suspend fun deleteProduct(
        id: Int,
    ): String
    suspend fun deletePerishes(
        id: Int,
    ): String

    suspend fun getAllPerishes(): List<ProductEntity>

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
        buyPrice: Long
    ): String

    suspend fun deleteInvoiceItem(id: String): String


    suspend fun deleteInvoice(
        id: String,
    ): String

    suspend fun getAllInvoices(): CustomerAndResInvoicesEntity

    suspend fun showInvoice(id: String): SaleInvoiceEntity

}

data class InvoiceItem(
    val productId: Int,
    val quantity: Int,
    val sellingPrice: Int,
    val buyPrice: Int
)