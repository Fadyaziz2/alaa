package com.alaa.domain.entity


data class AccountCustomer(
    val id:Int,
    val name:String,
    val status:String,
    val createdAt:String,
    val updatedAt:String,
    val mobile: String,
    val accountInvoicesCount:Double?,
    val accountTotalInvoice:Double?,
    val invoices:List<Invoice>
)
