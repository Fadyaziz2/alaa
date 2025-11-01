package com.alaa.alaagallo.view.home.search_comp.search_response

data class DataSearch(
    val compatibilities: List<Compatibility>,
    val id: Int,
    val image: String,
    val name: String
)