package com.eliasjuniornino.budgetplanner.repositories

import com.eliasjuniornino.budgetplanner.dto.expense_categories.ExpenseCategoryDTO
import com.eliasjuniornino.budgetplanner.dto.expense_categories.CreateExpenseCategoryDTO
import com.eliasjuniornino.budgetplanner.dto.expense_categories.UpdateExpenseCategoryDTO

interface ExpenseCategoryRepository {
    suspend fun list(): List<ExpenseCategoryDTO>
    suspend fun listSub(id: Int): List<ExpenseCategoryDTO>
    suspend fun view(id: Int): ExpenseCategoryDTO
    suspend fun create(data: CreateExpenseCategoryDTO): ExpenseCategoryDTO
    suspend fun update(data: UpdateExpenseCategoryDTO): ExpenseCategoryDTO
    suspend fun delete(id: Int): Boolean
}