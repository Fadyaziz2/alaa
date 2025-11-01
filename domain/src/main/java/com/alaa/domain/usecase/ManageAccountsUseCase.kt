package com.alaa.domain.usecase

import com.alaa.domain.repository.IRepository
import java.io.File
import javax.inject.Inject

class ManageAccountsUseCase @Inject constructor(
    private val repository: IRepository
) {
    suspend fun getCategoriesAndUsers() = repository.getCategoriesAndUsers()
    suspend fun addCategory(name: String) = repository.addCategory(name)
    suspend fun getAccountsInvoices(clientId: Int, sort: String) =
        repository.getAccountsInvoices(clientId, sort)

    suspend fun uploadOperation(
        customerId: Int,
        amount: String,
        dateTime: String,
        type: String,
        additionalNote: String,
        image: File
    ) = repository.uploadOperation(
        customerId,
        amount,
        dateTime,
        type,
        additionalNote,
        image
    )

    suspend fun updateInvoice(
        invoiceId: Int,
        accountCustomerId: Int,
        amount: String,
        date: String,
        type: String,
        additionalNote: String,
        image: File?
    ) = repository.updateInvoice(
        invoiceId = invoiceId,
        accountCustomerId = accountCustomerId,
        date = date,
        amount = amount,
        type = type,
        additionalNote = additionalNote,
        image = image
    )

    suspend fun addAccountClient(name: String, mobile: String, categoryId: Int) =
        repository.addAccountClient(
            name = name, mobile = mobile, categoryId = categoryId
        )

    suspend fun editAccountClient(name: String, mobile: String, categoryId: Int, clientId: Int) =
        repository.editAccountClient(
            name = name, mobile = mobile, categoryId = categoryId, clientId = clientId
        )

    suspend fun deleteAccountClient(clientId: Int) = repository.deleteAccountClient(clientId)

    suspend fun deleteCategory(categoryId: Int) =
        repository.deleteCategory(categoryId)

    suspend fun editCategory(categoryId: Int, name: String) =
        repository.editCategory(categoryId = categoryId, name = name)

    suspend fun deleteInvoice(invoiceId: Int) = repository.deleteInvoice(invoiceId)

    suspend fun getInvoice(invoiceId: Int) = repository.getInvoice(invoiceId)
}