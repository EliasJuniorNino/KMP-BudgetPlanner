package com.eliasjuniornino.budgetplanner.repositories.expenses_categories

import com.eliasjuniornino.budgetplanner.models.ExpenseCategoryModel
import com.eliasjuniornino.budgetplanner.models.CreateExpenseCategoryModel

interface ExpenseCategoriesRepository {
    suspend fun list(userId: Int): List<ExpenseCategoryModel>
    suspend fun list(userId: Int, parentCategoryId: Int): List<ExpenseCategoryModel>
    suspend fun store(userId: Int, data: CreateExpenseCategoryModel): ExpenseCategoryModel
    suspend fun get(userId: Int, categoryId: Int): ExpenseCategoryModel?
    suspend fun update(userId: Int, data: ExpenseCategoryModel): ExpenseCategoryModel
    suspend fun delete(userId: Int, categoryId: Int): Boolean
    suspend fun existsByName(userId: Int, name: String): Boolean
}