package com.alaa.alaagallo.model.numbers

import java.io.Serializable

data class DataNumbers(
    val image: String,
    val name: String,
    val number: String,
    val numbers: List<Numbers>,
): Serializable{
    override fun toString(): String {
        return "DataNumbers(image='$image', name='$name', number='$number', numbers=$numbers)"
    }
}
data class Numbers (
    val id :String,
    val number_id :String,
    val name :String,
    val number :String,
    val created_at :String,
    val updated_at :String,
): Serializable{
    override fun toString(): String {
        return "Numbers(id='$id', number_id='$number_id', name='$name', number='$number', created_at='$created_at', updated_at='$updated_at')"
    }
}