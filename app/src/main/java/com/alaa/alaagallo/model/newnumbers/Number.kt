package com.alaa.alaagallo.model.newnumbers

import java.io.Serializable

data class Number(
    val id: Int,
    val image: String,
    val name: String,
    val number: String,
    val numbers: List<NumberX>
): Serializable{
    override fun toString(): String {
        return "Number(name='$name')"
    }
}