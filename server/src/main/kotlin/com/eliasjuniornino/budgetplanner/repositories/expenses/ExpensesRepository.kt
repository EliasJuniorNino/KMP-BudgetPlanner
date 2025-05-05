package com.eliasjuniornino.budgetplanner.repositories.expenses

import com.eliasjuniornino.budgetplanner.models.CreateExpenseModel
import com.eliasjuniornino.budgetplanner.models.ExpenseModel

interface ExpensesRepository {
    suspend fun list(userId: Int): List<ExpenseModel>
    suspend fun store(userId: Int, data: CreateExpenseModel): ExpenseModel
    suspend fun get(userId: Int, id: Int): ExpenseModel?
    suspend fun update(userId: Int, data: ExpenseModel): ExpenseModel
    suspend fun delete(userId: Int, id: Int): Boolean
    suspend fun existsByName(userId: Int, name: String): Boolean
}