package com.alaa.data.dto


import com.google.gson.annotations.SerializedName

data class CustomerAndResInvoicesDto(
    @SerializedName("CustomerInvoices")
    val customerInvoices: List<SaleInvoiceDto>?,
    @SerializedName("ResourcesInvoices")
    val resourcesInvoices: List<SaleInvoiceDto>?
)

data class SaleInvoiceDto(
    @SerializedName("created_at")
    val createdAt: String?,

    @SerializedName("sale_user_name")
    val saleUserName: String?,
    @SerializedName("id")
    val id: Int?,
    @SerializedName("items")
    val items: List<ItemDto>?,
    @SerializedName("total")
    val total: Double?,
    @SerializedName("type")
    val type: String?,
    @SerializedName("updated_at")
    val updatedAt: String?
)
data class ItemDto(
    @SerializedName("buy_price")
    val buyPrice: Int?,
    @SerializedName("id")
    val id: Int?,
    @SerializedName("product")
    val product: String?,
    @SerializedName("quantity")
    val quantity: Int?,
    @SerializedName("selling_price")
    val sellingPrice: Int?,
    @SerializedName("total")
    val total: Int?
)