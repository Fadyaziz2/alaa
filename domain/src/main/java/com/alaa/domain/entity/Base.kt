package com.alaa.domain.entity

data class Base<T>(
    val statusCode: Int,
    val data: T
)
