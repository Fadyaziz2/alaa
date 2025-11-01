package com.alaa.domain.usecase

import com.alaa.domain.entity.ProductEntity
import com.alaa.domain.entity.ProductLimitEntity
import com.alaa.domain.entity.ProductReturnEntity
import com.alaa.domain.entity.User
import com.alaa.domain.repository.IRepository
import javax.inject.Inject

class ManageProductUseCase @Inject constructor(
    private val repository: IRepository
) {
    suspend fun getAllProduct(): List<ProductEntity> {
        return repository.getAllProduct()
    }

    suspend fun getAllProductLimit(): ProductLimitEntity {
        return repository.getAllProductLimit()
    }

    suspend fun getAllOutOfStock(): ProductLimitEntity {
        return repository.getAllOutOfStock()
    }

    suspend fun getAllRefunds(): List<ProductReturnEntity> {
        return repository.getAllRefunds()
    }

    suspend fun addProductRefund(
        productId: String,
        quantity: Long,
    ): String {
        return repository.addProductRefund(productId, quantity)
    }

    suspend fun updateProductRefund(
        id: String,
        productId: String,
        quantity: Long,
    ): String {
        return repository.updateProductRefund(id, productId, quantity)
    }

    suspend fun addProduct(
        name: String,
        stock: Long,
        minimumStock: Long,
        sellingPrice: Long,
        buyPrice: Long,
        status: String,
    ): String {
        return repository.addProduct(name, stock, minimumStock, sellingPrice, buyPrice, status)
    }

    suspend fun updateProduct(
        id: String,
        name: String,
        stock: Long,
        minimumStock: Long,
        sellingPrice: Long,
        buyPrice: Long,
        status: String,
    ): String {
        return repository.updateProduct(
            id,
            name,
            stock,
            minimumStock,
            sellingPrice,
            buyPrice,
            status
        )
    }

    suspend fun deleteProduct(
        id: Int,
    ): String {
        return repository.deleteProduct(id)
    }
}