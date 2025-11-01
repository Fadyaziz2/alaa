package com.alaa.domain.usecase

import com.alaa.domain.entity.ProductEntity
import com.alaa.domain.entity.ProductLimitEntity
import com.alaa.domain.entity.User
import com.alaa.domain.repository.IRepository
import javax.inject.Inject

class ManagePerishesUseCase @Inject constructor(
    private val repository: IRepository
) {
    suspend fun getAllPerishes(): List<ProductEntity> {
        return repository.getAllPerishes()
    }

    suspend fun addPerishes(
        name: String,
        price: Long,
        date: String,
    ): String {
        return repository.addPerishes(name, price, date)
    }

    suspend fun updatePerishes(
        id: String,
        name: String,
        price: Long,
        date: String,
    ): String {
        return repository.updatePerishes(
            id, name, price, date
        )
    }

    suspend fun deletePerishes(
        id: Int,
    ): String {
        return repository.deletePerishes(id)
    }
}