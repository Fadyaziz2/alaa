package com.alaa.data.repository

import android.util.Log
import com.alaa.data.dto.toEntity
import com.alaa.data.mapper.toEntity
import com.alaa.data.source.RemoteDataSource
import com.alaa.domain.entity.CustomerAndResInvoicesEntity
import com.alaa.domain.entity.CustomerAndResources
import com.alaa.domain.entity.ExpensesEntity
import com.alaa.domain.entity.ItemEntity
import com.alaa.domain.entity.ProductEntity
import com.alaa.domain.entity.ProductLimitEntity
import com.alaa.domain.entity.ProductReturnEntity
import com.alaa.domain.entity.SaleInvoiceEntity
import com.alaa.domain.repository.IRepository
import com.alaa.domain.repository.InvoiceItem
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.asRequestBody
import java.io.File
import javax.inject.Inject

class Repository @Inject constructor(
    private val remoteDataSource: RemoteDataSource,
) : IRepository {
    override suspend fun getCategoriesAndUsers() =
        remoteDataSource.getCategoriesAndUsers().map { item ->
            item.toEntity()
        }

    override suspend fun addCategory(name: String) = remoteDataSource.addCategory(name)
    override suspend fun getAccountsInvoices(clientId: Int, sort: String) =
        remoteDataSource.getAccountsInvoices(clientId, sort).toEntity()

    override suspend fun uploadOperation(
        customerId: Int,
        amount: String,
        dateTime: String,
        type: String,
        additionalNote: String,
        image: File
    ) = remoteDataSource.uploadOperation(
        prepareRequestBody(
            customerId,
            amount,
            dateTime,
            type,
            additionalNote,
            image
        )
    )

    override suspend fun getAllUsers(): CustomerAndResources =
        remoteDataSource.getAllUsers().toEntity()

    override suspend fun addUser(
        name: String,
        mobile: String,
        type: String,
        note: String
    ): String {
        return remoteDataSource.addUser(
            name,
            mobile,
            type,
            note
        )
    }

    override suspend fun updateUser(
        id: String,
        name: String,
        mobile: String,
        type: String,
        note: String
    ): String {
        return remoteDataSource.updateUser(
            id,
            name,
            mobile,
            type,
            note
        )
    }

    override suspend fun deleteUser(id: Int): String {
        return remoteDataSource.deleteUser(
            id,
        )
    }

    override suspend fun getAllExpenseses(): List<ExpensesEntity> {
        return remoteDataSource.getAllExpenseses().map { it.toEntity() }
    }

    override suspend fun deleteExpenseses(id: Int): String {
        return remoteDataSource.deleteExpenseses(
            id,
        )
    }

    override suspend fun addExpenseses(
        exchangePartyName: String,
        date: String,
        amount: String
    ): String {
        return remoteDataSource.addExpenseses(
            exchangePartyName, date, amount
        )
    }

    override suspend fun updateExpenseses(
        id: String,
        exchangePartyName: String,
        date: String,
        amount: String
    ): String {
        return remoteDataSource.updateExpenseses(
            id, exchangePartyName, date, amount
        )
    }

    override suspend fun getAllProduct(): List<ProductEntity> {
        return remoteDataSource.getAllProduct().map { it.toEntity() }
    }

    override suspend fun getAllOutOfStock(): ProductLimitEntity {
        val dto = remoteDataSource.getAllOutOfStock()
        return ProductLimitEntity(
            products = dto.products?.map { it.toEntity() } ?: emptyList(),
            count = dto.count ?: 0
        )
    }

    override suspend fun getAllProductLimit(): ProductLimitEntity {
        val dto = remoteDataSource.getAllProductLimit()
        return ProductLimitEntity(
            products = dto.products?.map { it.toEntity() } ?: emptyList(),
            count = dto.count ?: 0
        )
    }

    override suspend fun getAllRefunds(): List<ProductReturnEntity> {
        val dto = remoteDataSource.getAllRefunds()
        return dto.map { it.toEntity() }
    }

    override suspend fun addProductRefund(productId: String, quantity: Long): String {
        return remoteDataSource.addProductRefund(productId, quantity)
    }

    override suspend fun updateProductRefund(
        id: String,
        productId: String,
        quantity: Long
    ): String {
        return remoteDataSource.updateProductRefund(id, productId, quantity)
    }

    override suspend fun addProduct(
        name: String,
        stock: Long,
        minimumStock: Long,
        sellingPrice: Long,
        buyPrice: Long,
        status: String
    ): String {
        return remoteDataSource.addProduct(
            name,
            stock,
            minimumStock,
            sellingPrice,
            buyPrice,
            status
        )
    }

    override suspend fun updateProduct(
        id: String,
        name: String,
        stock: Long,
        minimumStock: Long,
        sellingPrice: Long,
        buyPrice: Long,
        status: String
    ): String {
        return remoteDataSource.updateProduct(
            id,
            name,
            stock,
            minimumStock,
            sellingPrice,
            buyPrice,
            status
        )
    }

    override suspend fun deleteProduct(id: Int): String {
        return remoteDataSource.deleteProduct(id)
    }

    override suspend fun deletePerishes(id: Int): String {
        return remoteDataSource.deletePerishes(id)
    }

    override suspend fun getAllPerishes(): List<ProductEntity> {
        return remoteDataSource.getAllPerishes().map { it.toEntity() }
    }

    override suspend fun addPerishes(name: String, price: Long, date: String): String {
        return remoteDataSource.addPerishes(name, price, date)
    }

    override suspend fun updatePerishes(
        id: String,
        name: String,
        price: Long,
        date: String
    ): String {
        return remoteDataSource.updatePerishes(id, name, price, date)
    }

    override suspend fun addInvoice(
        userId: String,
        type: String,
        items: List<InvoiceItem>
    ): String {
        return remoteDataSource.addInvoice(userId, type, items.map {
            com.alaa.data.source.InvoiceItem(
                productId = it.productId,
                quantity = it.quantity,
                sellingPrice = it.sellingPrice,
                buyPrice = it.buyPrice
            )
        })
    }

    override suspend fun addInvoiceItems(
        saleInvoiceId: String,
        type: String,
        items: List<InvoiceItem>
    ): String {
        return remoteDataSource.addInvoiceItems(saleInvoiceId, type, items.map {
            com.alaa.data.source.InvoiceItem(
                productId = it.productId,
                quantity = it.quantity,
                sellingPrice = it.sellingPrice,
                buyPrice = it.buyPrice
            )
        })
    }

    override suspend fun updateInvoice(
        id: String,
        saleInvoiceId: String,
        productId: String,
        quantity: Long,
        sellingPrice: Long,
        buyPrice: Long
    ): String {
        return remoteDataSource.updateInvoice(
            id,
            saleInvoiceId,
            productId,
            quantity,
            sellingPrice,
            buyPrice = buyPrice
        )
    }

    override suspend fun deleteInvoiceItem(id: String): String {
        return remoteDataSource.deleteInvoiceItem(id)
    }

    override suspend fun deleteInvoice(id: String): String {
        return remoteDataSource.deleteInvoice(id)
    }

    override suspend fun getAllInvoices(): CustomerAndResInvoicesEntity {
        val dto = remoteDataSource.getAllInvoices()
        return CustomerAndResInvoicesEntity(
            customerInvoices = dto.customerInvoices?.map {
                SaleInvoiceEntity(
                    createdAt = it.createdAt ?: "",
                    id = it.id ?: 0,
                    items = it.items?.map {
                        ItemEntity(
                            buyPrice = it.buyPrice ?: 0,
                            id = it.id ?: 0,
                            product = it.product ?: "",
                            quantity = it.quantity ?: 0,
                            sellingPrice = it.sellingPrice ?: 0,
                            total = it.total ?: 0
                        )
                    } ?: emptyList(),
                    total = it.total ?: 0.0,
                    type = it.type ?: "",
                    updatedAt = it.updatedAt ?: "",
                    saleUserName = it.saleUserName ?: ""
                )
            } ?: emptyList(),
            resourcesInvoices = dto.resourcesInvoices?.map {
                SaleInvoiceEntity(
                    createdAt = it.createdAt ?: "",
                    id = it.id ?: 0,
                    items = it.items?.map {
                        ItemEntity(
                            buyPrice = it.buyPrice ?: 0,
                            id = it.id ?: 0,
                            product = it.product ?: "",
                            quantity = it.quantity ?: 0,
                            sellingPrice = it.sellingPrice ?: 0,
                            total = it.total ?: 0
                        )
                    } ?: emptyList(),
                    total = it.total ?: 0.0,
                    type = it.type ?: "",
                    updatedAt = it.updatedAt ?: "",
                    saleUserName = it.saleUserName ?: "",
                )
            } ?: emptyList(),
        )
    }

    override suspend fun showInvoice(id: String): SaleInvoiceEntity {
        val dto = remoteDataSource.showInvoice(id)
        return SaleInvoiceEntity(
            createdAt = dto.createdAt ?: "",
            saleUserName = dto.saleUserName ?: "",
            id = dto.id ?: 0,
            items = dto.items?.map {
                ItemEntity(
                    buyPrice = it.buyPrice ?: 0,
                    id = it.id ?: 0,
                    product = it.product ?: "",
                    quantity = it.quantity ?: 0,
                    sellingPrice = it.sellingPrice ?: 0,
                    total = it.total ?: 0
                )
            } ?: emptyList(),
            total = dto.total ?: 0.0,
            type = dto.type ?: "",
            updatedAt = dto.updatedAt ?: ""
        )
    }

    override suspend fun updateInvoice(
        invoiceId: Int,
        accountCustomerId: Int,
        date: String,
        amount: String,
        type: String,
        additionalNote: String,
        image: File?
    ) = remoteDataSource.updateInvoice(
        invoiceId = invoiceId,
        prepareRequestBodyWithNull(
            customerId = accountCustomerId,
            amount = amount,
            dateTime = date,
            type = type,
            additionalNote = additionalNote,
            image = image
        )
    )

    override suspend fun addAccountClient(name: String, mobile: String, categoryId: Int) =
        remoteDataSource.addAccountClient(
            name = name, mobile = mobile, categoryId = categoryId
        )

    override suspend fun editAccountClient(
        name: String,
        mobile: String,
        categoryId: Int,
        clientId: Int
    ) =
        remoteDataSource.editAccountClient(
            name = name, mobile = mobile, categoryId = categoryId, clientId = clientId
        )

    override suspend fun deleteAccountClient(clientId: Int) =
        remoteDataSource.deleteAccountClient(clientId)

    override suspend fun deleteCategory(categoryId: Int) =
        remoteDataSource.deleteCategory(categoryId)

    override suspend fun editCategory(categoryId: Int, name: String) =
        remoteDataSource.editCategory(categoryId = categoryId, name = name)

    override suspend fun deleteInvoice(invoiceId: Int) = remoteDataSource.deleteInvoice(invoiceId)

    override suspend fun getInvoice(invoiceId: Int) =
        remoteDataSource.getInvoice(invoiceId).toEntity()

    private fun prepareRequestBody(
        customerId: Int,
        amount: String,
        dateTime: String,
        type: String,
        additionalNote: String,
        image: File
    ): RequestBody {
        val builder = MultipartBody.Builder().setType(MultipartBody.FORM)
        builder.addFormDataPart("account_customer_id", customerId.toString())
            .addFormDataPart("amount", amount)
            .addFormDataPart("date_time", dateTime)
            .addFormDataPart("type", type)
            .addFormDataPart("more_info", additionalNote)

        builder.addFormDataPart(
            "accountinvoice",
            image.getName(),
            image.asRequestBody("image/jpg".toMediaTypeOrNull())
        )
        return builder.build()
    }

    private fun prepareRequestBodyWithNull(
        customerId: Int,
        amount: String,
        dateTime: String,
        type: String,
        additionalNote: String,
        image: File?
    ): RequestBody {
        val builder = MultipartBody.Builder().setType(MultipartBody.FORM)
        builder.addFormDataPart("account_customer_id", customerId.toString())
            .addFormDataPart("amount", amount)
            .addFormDataPart("date_time", dateTime)
            .addFormDataPart("type", type)
            .addFormDataPart("more_info", additionalNote)

        if (image != null) builder.addFormDataPart(
            "accountinvoice",
            image.getName(),
            image.asRequestBody("image/jpg".toMediaTypeOrNull())
        )
        return builder.build()
    }
}