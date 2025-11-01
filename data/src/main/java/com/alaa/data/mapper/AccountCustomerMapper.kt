package com.alaa.data.mapper

import com.alaa.data.dto.AccountCustomerDto
import com.alaa.domain.entity.AccountCustomer

fun AccountCustomerDto.toEntity() = AccountCustomer(
    id = id,
    name = name,
    createdAt = createdAt,
    updatedAt = updatedAt,
    status = status,
    mobile = mobile,
    accountTotalInvoice = accountTotalInvoice,
    accountInvoicesCount = accountInvoicesCount,
    invoices = accountInvoices.map { item -> item.toEntity() }
)