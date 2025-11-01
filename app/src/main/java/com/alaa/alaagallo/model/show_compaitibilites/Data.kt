package com.alaa.alaagallo.model.show_compaitibilites

data class Data(
    val compatibilites: List<Compatibilite>,
    val created_at: String,
    val id: Int,
    val name: String,
    val updated_at: String
)