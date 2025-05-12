package com.eliasjuniornino.budgetplanner.repositories.expenses_categories

import com.eliasjuniornino.budgetplanner.models.ExpenseCategoryModel
import com.eliasjuniornino.budgetplanner.models.CreateExpenseCategoryModel

interface ExpenseCategoriesRepository {
    suspend fun list(accountId: Int): List<ExpenseCategoryModel>
    suspend fun list(accountId: Int, parentCategoryId: Int): List<ExpenseCategoryModel>
    suspend fun store(accountId: Int, data: CreateExpenseCategoryModel): ExpenseCategoryModel
    suspend fun get(accountId: Int, categoryId: Int): ExpenseCategoryModel?
    suspend fun update(accountId: Int, data: ExpenseCategoryModel): ExpenseCategoryModel
    suspend fun delete(accountId: Int, categoryId: Int): Boolean
    suspend fun existsByName(accountId: Int, name: String): Boolean
}