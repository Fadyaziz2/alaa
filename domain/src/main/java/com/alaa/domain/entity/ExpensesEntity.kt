package com.alaa.domain.entity


data class ExpensesEntity(
    val id: String,
    val exchange_party_name: String,
    val date: String,
    val amount: Long,
)