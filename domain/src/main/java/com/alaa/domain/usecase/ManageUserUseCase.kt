package com.alaa.domain.usecase

import com.alaa.domain.entity.User
import com.alaa.domain.repository.IRepository
import javax.inject.Inject

class ManageUserUseCase @Inject constructor(
    private val repository: IRepository
) {
    suspend fun getAllCustomerUsers():
            List<User> =
        repository.getAllUsers().customers

    suspend fun getAllSupplierUsers():
            List<User> =
        repository.getAllUsers().resources

    suspend fun addCustomerUser(
        name: String,
        mobile: String,
        status: String
    ): String {
        return repository.addUser(
            name,
            mobile,
            "customer",
            status
        )
    }

    suspend fun addSupplierUser(
        name: String,
        mobile: String,
        note: String
    ): String {
        return repository.addUser(
            name,
            mobile,
            "resource",
            note
        )
    }

    suspend fun updateCustomerUser(
        id: String,
        name: String,
        mobile: String,
        note: String
    ): String {
        return repository.updateUser(
            id,
            name,
            mobile,
            "customer",
            note
        )
    }
    suspend fun updateSupplierUser(
        id: String,
        name: String,
        mobile: String,
        note: String
    ): String {
        return repository.updateUser(
            id,
            name,
            mobile,
            "resource",
            note
        )
    }

    suspend fun deleteUser(id: Int): String {
        return repository.deleteUser(
            id,
        )
    }
}