package com.alaa.domain.entity



data class AccountInvoice(
    val id: Int,
    val accountTotalInvoice: Double,
    val accountInvoicesCount: Int,
    val totalOfCreditorInvoices: Double,
    val totalOfDebtorInvoices: Double,
    val invoices: List<Invoice>,
)

data class Invoice(
    val id: Int,
    val accountCustomerId: Int,
    val amount: Int,
    val dateTime: String,
    val updatedAt: String,
    val createdAt: String,
    val type: String,
    val media: String,
    val moreInfo: String,
)
