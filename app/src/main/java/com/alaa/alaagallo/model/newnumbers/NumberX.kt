package com.alaa.alaagallo.model.newnumbers

import java.io.Serializable

data class NumberX(
    val id: Int,
    val image: String,
    val name: String,
    val number: String,
    val numbers: List<Any>
): Serializable{
    override fun toString(): String {
        return "NumberX(name='$name')"
    }
}