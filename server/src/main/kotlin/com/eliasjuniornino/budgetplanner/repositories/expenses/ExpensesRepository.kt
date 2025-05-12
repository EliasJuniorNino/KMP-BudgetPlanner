package com.eliasjuniornino.budgetplanner.repositories.expenses

import com.eliasjuniornino.budgetplanner.models.CreateExpenseModel
import com.eliasjuniornino.budgetplanner.models.ExpenseModel

interface ExpensesRepository {
    suspend fun list(accountId: Int): List<ExpenseModel>
    suspend fun store(accountId: Int, data: CreateExpenseModel): ExpenseModel
    suspend fun get(accountId: Int, id: Int): ExpenseModel?
    suspend fun update(accountId: Int, data: ExpenseModel): ExpenseModel
    suspend fun delete(accountId: Int, id: Int): Boolean
    suspend fun existsByName(accountId: Int, name: String): Boolean
}