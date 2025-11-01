package com.alaa.alaagallo.model.newnumbers

import java.io.Serializable

data class Data(
    val id: Int,
    val image: String,
    val name: String,
    val number: Any,
    val numbers: List<Number>
):Serializable{
    override fun toString(): String {
        return "Data(name='$name')"
    }
}