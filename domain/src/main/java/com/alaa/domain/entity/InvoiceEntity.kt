package com.alaa.domain.entity

data class CustomerAndResInvoicesEntity(
    val customerInvoices: List<SaleInvoiceEntity>,
    val resourcesInvoices: List<SaleInvoiceEntity>
)

data class SaleInvoiceEntity(
    val createdAt: String,
    val saleUserName: String,
    val id: Int,
    val items: List<ItemEntity>,
    val total: Double,
    val type: String,
    val updatedAt: String
)
data class ItemEntity(
    val buyPrice: Int,
    val id: Int,
    val product: String,
    val quantity: Int,
    val sellingPrice: Int,
    val total: Int
)