package com.alaa.data.service

import com.alaa.data.dto.BaseDto
import com.alaa.data.dto.BaseResponse
import com.alaa.data.dto.ProductDto
import com.alaa.data.dto.ProductLimitDto
import com.alaa.data.dto.ProductReturnDto
import retrofit2.Response
import retrofit2.http.DELETE
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface ProductApiService {
    @GET("products")
    suspend fun getAllProduct(): Response<BaseDto<List<ProductDto>>>

    @GET("products/request/imit")
    suspend fun getAllProductLimit(): Response<BaseDto<ProductLimitDto>>

    @GET("products/out/of/stock")
    suspend fun getAllOutOfStock(): Response<BaseDto<ProductLimitDto>>

    @GET("refunds")
    suspend fun getAllRefunds(): Response<BaseDto<List<ProductReturnDto>>>

    @FormUrlEncoded
    @POST("products")
    suspend fun addProduct(
        @Field("name") name: String,
        @Field("stock") stock: Long,
        @Field("minimum_stock") minimumStock: Long,
        @Field("selling_price") sellingPrice: Long,
        @Field("buy_price") buyPrice: Long,
        @Field("status") status: String,
    ): Response<BaseDto<BaseResponse>>

    @FormUrlEncoded
    @PUT("products/{id}")
    suspend fun updateProduct(
        @Path("id") id: String,
        @Field("name") name: String,
        @Field("stock") stock: Long,
        @Field("minimum_stock") minimumStock: Long,
        @Field("selling_price") sellingPrice: Long,
        @Field("buy_price") buyPrice: Long,
        @Field("status") status: String,
    ): Response<BaseDto<BaseResponse>>

    @FormUrlEncoded
    @POST("refunds")
    suspend fun addProductRefund(
        @Field("product_id") productId: String,
        @Field("quantity") quantity: Long,
    ): Response<BaseDto<BaseResponse>>

    @FormUrlEncoded
    @PUT("refunds/{id}")
    suspend fun updateProductRefund(
        @Path("id") id: String,
        @Field("product_id") productId: String,
        @Field("quantity") quantity: Long,
    ): Response<BaseDto<BaseResponse>>

    @DELETE("products/{id}")
    suspend fun deleteProduct(
        @Path("id") id: Int,
    ): Response<BaseDto<BaseResponse>>


    @GET("perisheds")
    suspend fun getAllPerishes(): Response<BaseDto<List<ProductDto>>>

    @FormUrlEncoded
    @POST("perisheds")
    suspend fun addPerishes(
        @Field("name") name: String,
        @Field("price") price: Long,
        @Field("date") date: String,
    ): Response<BaseDto<BaseResponse>>

    @FormUrlEncoded
    @PUT("perisheds/{id}")
    suspend fun updatePerishes(
        @Path("id") id: String,
        @Field("name") name: String,
        @Field("price") price: Long,
        @Field("date") date: String,
    ): Response<BaseDto<BaseResponse>>

    @DELETE("perisheds/{id}")
    suspend fun deletePerishes(
        @Path("id") id: Int,
    ): Response<BaseDto<BaseResponse>>
}

