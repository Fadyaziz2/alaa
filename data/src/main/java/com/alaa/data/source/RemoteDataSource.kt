package com.alaa.data.source

import android.util.Log
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
import com.alaa.data.service.AccountsApiService
import com.alaa.data.service.InvoiceApiService
import com.alaa.data.service.ProductApiService
import com.alaa.data.service.UserApiService
import com.alaa.domain.exceptions.NoInternetException
import com.alaa.domain.exceptions.NotFoundException
import com.alaa.domain.exceptions.NullResultException
import com.alaa.domain.exceptions.ServerErrorException
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import org.json.JSONObject
import retrofit2.Response
import java.io.IOException
import java.net.SocketException
import java.net.SocketTimeoutException
import java.net.UnknownHostException
import javax.inject.Inject

class RemoteDataSource @Inject constructor(
    private val apiService: AccountsApiService,
    private val userApiService: UserApiService,
    private val productApiService: ProductApiService,
    private val invoiceApiService: InvoiceApiService,
) : IRemoteDataSource {

    override suspend fun getCategoriesAndUsers(): List<CategoryDto> {
        return wrapApiResponse {
            apiService.getCategoriesAndUsers()
        }.data
    }

    override suspend fun addCategory(name: String): Boolean {
        wrapApiResponse {
            apiService.addCategory(name)
        }
        return true
    }

    override suspend fun getAccountsInvoices(clientId: Int, sort: String): AccountInvoiceDto {
        return wrapApiResponse {
            apiService.getAccountsInvoices(clientId, sort)
        }.data
    }

    override suspend fun uploadOperation(data: RequestBody): Boolean {
        wrapApiResponse {
            apiService.uploadOperation(data)
        }
        return true
    }

    override suspend fun getAllUsers(): CustomerAndResourcesDto {
        return wrapApiResponse {
            userApiService.getAllUsers()
        }.data
    }

    override suspend fun addUser(
        name: String,
        mobile: String,
        type: String,
        note: String
    ): String {
        return wrapApiResponse {
            userApiService.addUser(
                name = name,
                mobile = mobile,
                type = type,
                status = "active",
                note = note
            )
        }.data.message
    }

    override suspend fun updateUser(
        id: String,
        name: String,
        mobile: String,
        type: String,
        note: String
    ): String {
        return wrapApiResponse {
            userApiService.updateUser(
                id = id,
                name = name,
                mobile = mobile,
                type = type,
                status = "active",
                note = note
            )
        }.data.message
    }

    override suspend fun deleteUser(id: Int): String {
        return wrapApiResponse {
            userApiService.deleteUser(id)
        }.data.message
    }

    override suspend fun deleteExpenseses(id: Int): String {
        return wrapApiResponse {
            userApiService.deleteExpenseses(id)
        }.message
    }

    override suspend fun getAllExpenseses(): List<ExpensesDto> {
        return wrapApiResponse {
            userApiService.getAllExpenseses()
        }.data
    }

    override suspend fun addExpenseses(
        exchangePartyName: String,
        date: String,
        amount: String
    ): String {
        return wrapApiResponse {
            userApiService.addExpenseses(
                exchangePartyName = exchangePartyName,
                date = date,
                amount = amount
            )
        }.message
    }

    override suspend fun updateExpenseses(
        id: String,
        exchangePartyName: String,
        date: String,
        amount: String
    ): String {
        return wrapApiResponse {
            userApiService.updateExpenseses(
                id,
                exchangePartyName = exchangePartyName,
                date = date,
                amount = amount
            )
        }.message
    }

    override suspend fun getAllProduct(): List<ProductDto> {
        return wrapApiResponse {
            productApiService.getAllProduct()
        }.data
    }

    override suspend fun getAllOutOfStock(): ProductLimitDto {
        return wrapApiResponse {
            productApiService.getAllOutOfStock()
        }.data
    }

    override suspend fun getAllProductLimit(): ProductLimitDto {
        return wrapApiResponse {
            productApiService.getAllProductLimit()
        }.data
    }

    override suspend fun getAllRefunds(): List<ProductReturnDto> {
        return wrapApiResponse {
            productApiService.getAllRefunds()
        }.data
    }

    override suspend fun addProduct(
        name: String,
        stock: Long,
        minimumStock: Long,
        sellingPrice: Long,
        buyPrice: Long,
        status: String
    ): String {
        return wrapApiResponse {
            productApiService.addProduct(
                name = name,
                stock = stock,
                minimumStock = minimumStock,
                sellingPrice = sellingPrice,
                buyPrice = buyPrice,
                status = status
            )
        }.message
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
        return wrapApiResponse {
            productApiService.updateProduct(
                id = id,
                name = name,
                stock = stock,
                minimumStock = minimumStock,
                sellingPrice = sellingPrice,
                buyPrice = buyPrice,
                status = status
            )
        }.message
    }

    override suspend fun addProductRefund(productId: String, quantity: Long): String {
        return wrapApiResponse {
            productApiService.addProductRefund(
                productId = productId,
                quantity = quantity
            )
        }.message
    }

    override suspend fun updateProductRefund(
        id: String,
        productId: String,
        quantity: Long
    ): String {
        return wrapApiResponse {
            productApiService.updateProductRefund(
                id = id,
                productId = productId,
                quantity = quantity
            )
        }.message
    }

    override suspend fun deleteProduct(id: Int): String {
        return wrapApiResponse {
            productApiService.deleteProduct(
                id = id
            )
        }.message
    }

    override suspend fun deletePerishes(id: Int): String {
        return wrapApiResponse {
            productApiService.deletePerishes(
                id = id
            )
        }.message
    }

    override suspend fun getAllPerishes(): List<ProductDto> {
        return wrapApiResponse {
            productApiService.getAllPerishes()
        }.data
    }

    override suspend fun addPerishes(name: String, price: Long, date: String): String {
        return wrapApiResponse {
            productApiService.addPerishes(
                name, price, date
            )
        }.message
    }

    override suspend fun updatePerishes(
        id: String,
        name: String,
        price: Long,
        date: String
    ): String {
        return wrapApiResponse {
            productApiService.updatePerishes(
                id, name, price, date
            )
        }.message
    }

    override suspend fun getAllInvoices(): CustomerAndResInvoicesDto {
        return wrapApiResponse {
            invoiceApiService.getAllInvoices()
        }.data
    }

    override suspend fun showInvoice(id: String): SaleInvoiceDto {
        return wrapApiResponse {
            invoiceApiService.showInvoice(id)
        }.data
    }

    override suspend fun addInvoice(
        userId: String,
        type: String,
        items: List<InvoiceItem>
    ): String {

        val itemParts = items.flatMapIndexed { index, item ->
            createItemParts(index, item)
        }

        return wrapApiResponse {
            invoiceApiService.addInvoice(
                createPartFromString(userId),
                createPartFromString(type),
                itemParts
            )
        }.data.message
    }

    override suspend fun addInvoiceItems(
        saleInvoiceId: String,
        type: String,
        items: List<InvoiceItem>
    ): String {
        val itemParts = items.flatMapIndexed { index, item ->
            createItemParts(index, item )
        }
        return wrapApiResponse {
            invoiceApiService.addInvoiceItems(
                createPartFromString(saleInvoiceId),
                createPartFromString(type),
                itemParts
            )
        }.data.message
    }

    override suspend fun updateInvoice(
        id: String,
        saleInvoiceId: String,
        productId: String,
        quantity: Long,
        sellingPrice: Long,
        buyPrice: Long
    ): String {
        return wrapApiResponse {
            invoiceApiService.updateInvoice(
                id, saleInvoiceId, productId, quantity, sellingPrice, buyPrice
            )
        }.data.message
    }

    override suspend fun deleteInvoiceItem(id: String): String {
        return wrapApiResponse {
            invoiceApiService.deleteInvoiceItem(
                id
            )
        }.data.message
    }

    override suspend fun deleteInvoice(id: String): String {
        return wrapApiResponse {
            invoiceApiService.deleteInvoice(
                id
            )
        }.data.message
    }

    override suspend fun updateInvoice(invoiceId: Int, data: RequestBody): Boolean {
        wrapApiResponse {
            apiService.updateInvoice(invoiceId, data)
        }
        return true
    }


    override suspend fun addAccountClient(name: String, mobile: String, categoryId: Int): Boolean {
        wrapApiResponse {
            apiService.addAccountClient(name = name, mobile = mobile, categoryId = categoryId)
        }
        return true
    }

    override suspend fun editAccountClient(
        name: String,
        mobile: String,
        categoryId: Int,
        clientId: Int
    ): Boolean {
        wrapApiResponse {
            apiService.editAccountClient(
                name = name,
                mobile = mobile,
                categoryId = categoryId,
                clientId = clientId
            )
        }
        return true
    }

    override suspend fun deleteAccountClient(clientId: Int): Boolean {
        wrapApiResponse {
            apiService.deleteAccountClient(clientId)
        }
        return true
    }

    override suspend fun deleteCategory(categoryId: Int): Boolean {
        wrapApiResponse {
            apiService.deleteCategory(categoryId)
        }
        return true
    }

    override suspend fun editCategory(categoryId: Int, name: String): Boolean {
        wrapApiResponse {
            apiService.editCategory(
                categoryId = categoryId,
                name = name
            )
        }
        return true
    }

    override suspend fun deleteInvoice(invoiceId: Int): Boolean {
        wrapApiResponse {
            apiService.deleteInvoice(invoiceId)
        }
        return true
    }

    override suspend fun getInvoice(invoiceId: Int): InvoiceDto {
        return wrapApiResponse {
            apiService.getInvoice(invoiceId)
        }.data
    }


    private suspend fun <T> wrapApiResponse(
        function: suspend () -> Response<T>,
    ): T {
        try {
            val response = function()
            if (response.isSuccessful)
                return response.body() ?: throw NullResultException("No Data")
            else {
                val jObjError = JSONObject(
                    response.errorBody()!!.string()
                )
//                val errorObject = jObjError.getJSONObject("error")
                throw when (response.code()) {
                    404 -> NotFoundException(jObjError.getString("message"))
                    else -> {
                        ServerErrorException(jObjError.getString("message"))
                    }
                }
            }
        } catch (e: UnknownHostException) {
            throw NoInternetException("لا يوجد انترنت")
        } catch (e: SocketTimeoutException) {
            throw NoInternetException("لا يوجد انترنت")
        } catch (e: SocketException) {
            throw NoInternetException("لا يوجد انترنت")
        } catch (io: IOException) {
            throw NoInternetException(io.message.toString())
        }
    }

    private fun createPartFromString(value: String): RequestBody {
        return value.toRequestBody("text/plain".toMediaTypeOrNull())
    }

    private fun createItemParts(
        index: Int,
        item: InvoiceItem,
    ): List<MultipartBody.Part> {
        val list = mutableListOf(
            MultipartBody.Part.createFormData("item[$index][product_id]", item.productId.toString()),
            MultipartBody.Part.createFormData("item[$index][quantity]", item.quantity.toString()),
            MultipartBody.Part.createFormData("item[$index][selling_price]", item.sellingPrice.toString()),
            MultipartBody.Part.createFormData("item[$index][buy_price]", item.buyPrice.toString())
        )
        return list
    }
}

data class InvoiceItem(
    val productId: Int,
    val quantity: Int,
    val sellingPrice: Int,
    val buyPrice: Int
)