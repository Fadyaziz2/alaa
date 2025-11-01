package com.alaa.data.mapper

import com.alaa.data.dto.CategoryDto
import com.alaa.domain.entity.Category

fun CategoryDto.toEntity() = Category(
    id = id,
    name = name,
    accountCustomers = accountCustomers.map { accountCustomer ->
        accountCustomer.toEntity()
    },
    totalReceivables = totalReceivables,
    totalDebt = totalDebt,
)