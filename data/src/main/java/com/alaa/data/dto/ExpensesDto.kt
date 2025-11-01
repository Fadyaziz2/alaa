package com.alaa.data.dto


import com.alaa.domain.entity.ExpensesEntity
import com.alaa.domain.entity.ProductEntity
import com.alaa.domain.entity.User
import com.google.gson.annotations.SerializedName

data class ExpensesDto(
    @SerializedName("id")
    val id: String?,
    @SerializedName("exchange_party_name")
    val exchange_party_name: String?,
    @SerializedName("date")
    val date: String?,
    @SerializedName("amount")
    val amount: Long?,
)

fun ExpensesDto.toEntity() = ExpensesEntity(
    id = id ?: "",
    exchange_party_name = exchange_party_name ?: "",
    date = date ?: "",
    amount = amount ?: 0L
)