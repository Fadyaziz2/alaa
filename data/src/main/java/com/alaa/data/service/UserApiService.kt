package com.alaa.data.service

import com.alaa.data.dto.BaseDto
import com.alaa.data.dto.BaseResponse
import com.alaa.data.dto.CustomerAndResourcesDto
import com.alaa.data.dto.ExpensesDto
import com.alaa.data.dto.UserDto
import com.google.gson.annotations.SerializedName
import retrofit2.Response
import retrofit2.http.DELETE
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface UserApiService {
    @GET("users")
    suspend fun getAllUsers(): Response<BaseDto<CustomerAndResourcesDto>>

    @FormUrlEncoded
    @POST("users")
    suspend fun addUser(
        @Field("name") name: String,
        @Field("mobile") mobile: String,
        @Field("type") type: String,
        @Field("status") status: String,
        @Field("note") note: String,
    ): Response<BaseDto<BaseResponse>>

    @FormUrlEncoded
    @PUT("users/{id}")
    suspend fun updateUser(
        @Path("id") id: String,
        @Field("name") name: String,
        @Field("mobile") mobile: String,
        @Field("type") type: String,
        @Field("status") status: String,
        @Field("note") note: String,
        ): Response<BaseDto<BaseResponse>>

    @DELETE("users/{id}")
    suspend fun deleteUser(
        @Path("id") id: Int,
    ): Response<BaseDto<BaseResponse>>


    @GET("expenseses")
    suspend fun getAllExpenseses(): Response<BaseDto<List<ExpensesDto>>>

    @FormUrlEncoded
    @POST("expenseses")
    suspend fun addExpenseses(
        @Field("exchange_party_name") exchangePartyName: String,
        @Field("date") date: String,
        @Field("amount") amount: String,
    ): Response<BaseDto<BaseResponse>>

    @FormUrlEncoded
    @PUT("expenseses/{id}")
    suspend fun updateExpenseses(
        @Path("id") id: String,
        @Field("exchange_party_name") exchangePartyName: String,
        @Field("date") date: String,
        @Field("amount") amount: String,
    ): Response<BaseDto<BaseResponse>>

    @DELETE("expenseses/{id}")
    suspend fun deleteExpenseses(
        @Path("id") id: Int,
    ): Response<BaseDto<BaseResponse>>
}

