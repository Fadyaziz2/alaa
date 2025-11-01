package com.alaa.domain.entity


data class ProductEntity(
    val barcode: String,
    val buyPrice: Long,
    val createdAt: String,
    val id: String,
    val minimumStock: Long,
    val name: String,
    val sellingPrice: Long,
    val status: String,
    val stock: Long,
    val updatedAt: String,
    val date: String,
    val price: Long,
)
data class ProductReturnEntity(
    val product: ProductEntity,
    val quantity: Int,
    val id: Int,
    val createdAt: String,
    val updatedAt: String,
)

data class ProductLimitEntity(
    val products: List<ProductEntity>,
    val count: Int,
)