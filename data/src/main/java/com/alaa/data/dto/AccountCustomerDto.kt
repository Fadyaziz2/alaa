package com.alaa.data.dto

import com.google.gson.annotations.SerializedName

data class AccountCustomerDto(
    val id: Int,
    val name: String,
    val status: String,
    @SerializedName("mobile")
    val mobile: String,
    @SerializedName("created_at")
    val createdAt: String,
    @SerializedName("updated_at")
    val updatedAt: String,
    @SerializedName("accountinvoices_count")
    val accountInvoicesCount: Double?,
    @SerializedName("account_total_invoice")
    val accountTotalInvoice: Double?,
    @SerializedName("accountinvoices")
    val accountInvoices:List<InvoiceDto>
)
