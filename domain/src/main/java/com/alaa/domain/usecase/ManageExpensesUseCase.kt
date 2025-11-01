package com.alaa.domain.usecase

import com.alaa.domain.entity.ExpensesEntity
import com.alaa.domain.entity.User
import com.alaa.domain.repository.IRepository
import javax.inject.Inject

class ManageExpensesUseCase @Inject constructor(
    private val repository: IRepository
) {
    suspend fun getAllExpenseses(): List<ExpensesEntity> = repository.getAllExpenseses()

    suspend fun addExpenseses(
        exchangePartyName: String,
        date: String,
        amount: String,
    ): String  = repository.addExpenseses(exchangePartyName, date, amount)

    suspend fun updateExpenseses(
        id: String,
        exchangePartyName: String,
        date: String,
        amount: String,
    ): String  = repository.updateExpenseses(id, exchangePartyName, date, amount)

    suspend fun deleteExpenseses(
        id: Int,
    ): String  = repository.deleteExpenseses(id)
}