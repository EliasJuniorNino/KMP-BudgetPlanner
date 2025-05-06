package com.eliasjuniornino.budgetplanner.repositories.incomes

import com.eliasjuniornino.budgetplanner.models.CreateIncomeModel
import com.eliasjuniornino.budgetplanner.models.IncomeModel

interface IncomesRepository {
    suspend fun list(userId: Int): List<IncomeModel>
    suspend fun store(userId: Int, data: CreateIncomeModel): IncomeModel
    suspend fun get(userId: Int, id: Int): IncomeModel?
    suspend fun update(userId: Int, data: IncomeModel): IncomeModel
    suspend fun delete(userId: Int, id: Int): Boolean
    suspend fun existsByName(userId: Int, name: String): Boolean
}