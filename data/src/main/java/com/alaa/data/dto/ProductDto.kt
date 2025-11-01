package com.alaa.data.dto


import com.alaa.domain.entity.ProductEntity
import com.alaa.domain.entity.ProductReturnEntity
import com.alaa.domain.entity.User
import com.google.gson.annotations.SerializedName

data class ProductDto(
    @SerializedName("barcode")
    val barcode: String?,
    @SerializedName("buy_price")
    val buyPrice: Long?,
    @SerializedName("created_at")
    val createdAt: String?,
    @SerializedName("id")
    val id: String?,
    @SerializedName("minimum_stock")
    val minimumStock: Long?,
    @SerializedName("name")
    val name: String?,
    @SerializedName("selling_price")
    val sellingPrice: Long?,
    @SerializedName("status")
    val status: String?,
    @SerializedName("stock")
    val stock: Long?,
    @SerializedName("updated_at")
    val updatedAt: String?,
    @SerializedName("date")
    val date: String?,
    @SerializedName("price")
    val price: Long?

)

data class ProductLimitDto(
    @SerializedName("products")
    val products: List<ProductDto>?,
    @SerializedName("count_of_products")
    val count: Int?,
    @SerializedName("quantity")
    val quantity: Int?,
)

data class ProductReturnDto(
    @SerializedName("product")
    val product: ProductDto?,
    @SerializedName("quantity")
    val quantity: Int?,
    @SerializedName("id")
    val id: Int?,
    @SerializedName("created_at")
    val createdAt: String?,
    @SerializedName("updated_at")
    val updatedAt: String?,
)

fun ProductReturnDto.toEntity() = ProductReturnEntity(
    product = product?.toEntity() ?: ProductEntity(
        barcode = "",
        buyPrice = 0L,
        createdAt = "",
        id = "",
        minimumStock = 0L,
        name = "",
        sellingPrice = 0L,
        status = "",
        stock = 0L,
        updatedAt = "",
        date = "",
        price = 0L
    ),
    quantity = quantity ?: 0,
    id = id ?: 0,
    createdAt = createdAt ?: "",
    updatedAt = updatedAt ?: ""
)


fun ProductDto.toEntity() = ProductEntity(
    barcode = barcode ?: "",
    buyPrice = buyPrice ?: 0L,
    createdAt = createdAt ?: "",
    id = id ?: "",
    minimumStock = minimumStock ?: 0L,
    name = name ?: "",
    sellingPrice = sellingPrice ?: 0L,
    status = status ?: "",
    stock = stock ?: 0L,
    updatedAt = updatedAt ?: "",
    date = date ?: "",
    price = price ?: 0L
)