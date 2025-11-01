package com.alaa.data.dto


import com.alaa.domain.entity.CustomerAndResources
import com.alaa.domain.entity.User
import com.google.gson.annotations.SerializedName

data class UserDto(
    @SerializedName("id")
    val id: Int?,
    @SerializedName("invoices")
    val invoices: List<Any?>?,
    @SerializedName("mobile")
    val mobile: String?,
    @SerializedName("name")
    val name: String?,
    @SerializedName("status")
    val status: String?,
    @SerializedName("type")
    val type: String?,
    @SerializedName("note")
    val note: String?

)

fun UserDto.toEntity() = User(
    id = id ?: 0,
    mobile = mobile ?: "",
    name = name ?: "",
    note = note ?: "",
)

data class CustomerAndResourcesDto(
    @SerializedName("customers")
    val customers: List<UserDto>?,
    @SerializedName("resources")
    val resources: List<UserDto>?,
)

fun CustomerAndResourcesDto.toEntity() = CustomerAndResources(
    customers = customers?.map { it.toEntity()} ?: emptyList(),
    resources = resources?.map { it.toEntity()} ?: emptyList(),
)
