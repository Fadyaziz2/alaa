package com.alaa.data.service

import com.alaa.data.dto.BaseDto
import com.alaa.data.dto.BaseResponse
import com.alaa.data.dto.CustomerAndResInvoicesDto
import com.alaa.data.dto.SaleInvoiceDto
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Response
import retrofit2.http.DELETE
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Part
import retrofit2.http.Path

interface InvoiceApiService {

    @GET("salesinvoices")
    suspend fun getAllInvoices(): Response<BaseDto<CustomerAndResInvoicesDto>>

    @GET("salesinvoices/{id}")
    suspend fun showInvoice(@Path("id") id: String): Response<BaseDto<SaleInvoiceDto>>

    @Multipart
    @POST("salesinvoicesitems")
    suspend fun addInvoice(
        @Part("sale_user_id") saleUserId: RequestBody,
        @Part("type") type: RequestBody,
        @Part items: List<MultipartBody.Part>
    ): Response<BaseDto<BaseResponse>>

    @Multipart
    @POST("salesinvoicesitems/add/item")
    suspend fun addInvoiceItems(
        @Part("sale_invoice_id") saleInvoiceId: RequestBody,
        @Part("type") type: RequestBody,
        @Part items: List<MultipartBody.Part>
    ): Response<BaseDto<BaseResponse>>


    @FormUrlEncoded
    @PUT("salesinvoicesitems/{id}")
    suspend fun updateInvoice(
        @Path("id") id: String,
        @Field("sale_invoice_id") saleInvoiceId: String,
        @Field("product_id") productId: String,
        @Field("quantity") quantity: Long,
        @Field("selling_price") sellingPrice: Long,
        @Field("buy_price") buyPrice: Long,
    ): Response<BaseDto<BaseResponse>>

    @DELETE("salesinvoicesitems/{id}")
    suspend fun deleteInvoiceItem(
        @Path("id") id: String,
    ): Response<BaseDto<BaseResponse>>

    @DELETE("salesinvoices/{id}")
    suspend fun deleteInvoice(
        @Path("id") id: String,
    ): Response<BaseDto<BaseResponse>>
}