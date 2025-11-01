package com.alaa.domain.usecase

import com.alaa.domain.entity.CustomerAndResInvoicesEntity
import com.alaa.domain.entity.SaleInvoiceEntity
import com.alaa.domain.repository.IRepository
import com.alaa.domain.repository.InvoiceItem
import javax.inject.Inject

class ManageInvoiceUseCase @Inject constructor(
    private val repository: IRepository
) {
    suspend fun addInvoice(
        userId: String,
        type: String,
        items: List<InvoiceItem>
    ): String {
        return repository.addInvoice(userId, type, items)
    }

    suspend fun addInvoiceItems(
        saleInvoiceId: String,
        type: String,
        items: List<InvoiceItem>
    ): String {
        return repository.addInvoiceItems(saleInvoiceId, type, items)

    }

    suspend fun updateInvoice(
        id: String,
        saleInvoiceId: String,
        productId: String,
        quantity: Long,
        sellingPrice: Long,
        buyPrice: Long
    ): String {
        return repository.updateInvoice(
            id,
            saleInvoiceId,
            productId,
            quantity,
            sellingPrice,
            buyPrice
        )

    }

    suspend fun deleteItemInvoice(id: String): String {
        return repository.deleteInvoiceItem(id)
    }

    suspend fun deleteInvoice(
        id: String,
    ): String {
        return repository.deleteInvoice(id)
    }

    suspend fun getAllInvoices(): CustomerAndResInvoicesEntity {
        return repository.getAllInvoices()
    }

    suspend fun showInvoice(id: String): SaleInvoiceEntity {
        return repository.showInvoice(id)
    }
}