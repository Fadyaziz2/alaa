package com.alaa.data.service

import com.alaa.data.dto.AccountInvoiceDto
import com.alaa.data.dto.BaseDto
import com.alaa.data.dto.CategoryDto
import com.alaa.data.dto.InvoiceDto
import okhttp3.RequestBody
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path
import retrofit2.http.Query

interface AccountsApiService {
    @GET("accounts/categories")
    suspend fun getCategoriesAndUsers(): Response<BaseDto<List<CategoryDto>>>

    @FormUrlEncoded
    @POST("accounts/categories")
    suspend fun addCategory(
        @Field("name") name: String
    ): Response<Any>

    @GET("accounts/customers/{id}")
    suspend fun getAccountsInvoices(
        @Path("id") id: Int,
        @Query("sort") sort: String = "asc",
    ): Response<BaseDto<AccountInvoiceDto>>

    @POST("accounts/invoices")
    suspend fun uploadOperation(
        @Body data: RequestBody,
    ): Response<BaseDto<Any>>

    @POST("accounts/invoices/{id}")
    suspend fun updateInvoice(
        @Path("id") invoiceId: Int,
        @Body data: RequestBody,
    ): Response<BaseDto<Any>>

    @POST("accounts/customers")
    @FormUrlEncoded
    suspend fun addAccountClient(
        @Field("name") name: String,
        @Field("mobile") mobile: String,
        @Field("account_category_id") categoryId: Int,
    ): Response<BaseDto<Any>>

    @PUT("accounts/customers/{id}")
    @FormUrlEncoded
    suspend fun editAccountClient(
        @Field("name") name: String,
        @Field("mobile") mobile: String,
        @Field("account_category_id") categoryId: Int,
        @Path("id") clientId: Int,
    ): Response<BaseDto<Any>>

    @DELETE("accounts/customers/{id}")
    suspend fun deleteAccountClient(
        @Path("id") id: Int,
    ): Response<BaseDto<Any>>

    @PUT("accounts/categories/{id}")
    @FormUrlEncoded
    suspend fun editCategory(
        @Path("id") categoryId: Int,
        @Field("name") name: String,
    ):Response<BaseDto<Any>>

    @DELETE("accounts/categories/{id}")
    suspend fun deleteCategory(
        @Path("id") categoryId: Int,
    ):Response<BaseDto<Any>>

    @GET("accounts/invoices/{id}")
    suspend fun getInvoice(
        @Path("id") invoiceId: Int,
    ):Response<BaseDto<InvoiceDto>>

    @DELETE("accounts/invoices/{id}")
    suspend fun deleteInvoice(
        @Path("id") invoiceId: Int,
    ):Response<BaseDto<Any>>
}