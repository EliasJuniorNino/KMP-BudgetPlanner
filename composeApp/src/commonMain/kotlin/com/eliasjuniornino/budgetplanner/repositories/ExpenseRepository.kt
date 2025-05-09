package com.eliasjuniornino.budgetplanner.repositories

import com.eliasjuniornino.budgetplanner.dto.expenses.CreateExpenseDTO
import com.eliasjuniornino.budgetplanner.dto.expenses.ExpenseDTO
import com.eliasjuniornino.budgetplanner.dto.expenses.UpdateExpenseDTO

interface ExpenseRepository {
    suspend fun list(): List<ExpenseDTO>
    suspend fun view(id: Int): ExpenseDTO
    suspend fun create(data: CreateExpenseDTO): ExpenseDTO
    suspend fun update(data: UpdateExpenseDTO): ExpenseDTO
    suspend fun delete(id: Int): Boolean
}