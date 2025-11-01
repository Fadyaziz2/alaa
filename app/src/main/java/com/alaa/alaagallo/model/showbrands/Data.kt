package com.alaa.alaagallo.model.showbrands

data class Data(
    val created_at: String,
    val id: Int,
    val image: String,
    val models: List<Model>,
    val name: String,
    val updated_at: String
)