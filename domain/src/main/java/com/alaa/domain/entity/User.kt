package com.alaa.domain.entity

data class User(
    val id: Int = 0,
    val mobile: String = "",
    val name: String = "",
    val note: String = "",
    val type: String = ""
)

data class CustomerAndResources(
    val customers: List<User> = emptyList(),
    val resources: List<User> = emptyList(),
)
