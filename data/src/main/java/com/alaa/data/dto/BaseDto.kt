package com.alaa.data.dto

data class BaseDto<T>(
    val statusCode: Int,
    val data: T,
    val message: String
)

data class BaseResponse(
    val statusCode: Int,
    val message: String
)
