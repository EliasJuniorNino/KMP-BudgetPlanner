package com.eliasjuniornino.budgetplanner.repositories.incomes

import com.eliasjuniornino.budgetplanner.models.CreateIncomeModel
import com.eliasjuniornino.budgetplanner.models.IncomeModel

interface IncomesRepository {
    suspend fun list(accountId: Int): List<IncomeModel>
    suspend fun store(accountId: Int, data: CreateIncomeModel): IncomeModel
    suspend fun get(accountId: Int, id: Int): IncomeModel?
    suspend fun update(accountId: Int, data: IncomeModel): IncomeModel
    suspend fun delete(accountId: Int, id: Int): Boolean
    suspend fun existsByName(accountId: Int, name: String): Boolean
}