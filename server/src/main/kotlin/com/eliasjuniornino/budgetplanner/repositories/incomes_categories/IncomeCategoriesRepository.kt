package com.eliasjuniornino.budgetplanner.repositories.incomes_categories

import com.eliasjuniornino.budgetplanner.models.IncomeCategoryModel
import com.eliasjuniornino.budgetplanner.models.CreateIncomeCategoryModel

interface IncomeCategoriesRepository {
    suspend fun list(accountId: Int): List<IncomeCategoryModel>
    suspend fun list(accountId: Int, parentCategoryId: Int): List<IncomeCategoryModel>
    suspend fun store(accountId: Int, data: CreateIncomeCategoryModel): IncomeCategoryModel
    suspend fun get(accountId: Int, categoryId: Int): IncomeCategoryModel?
    suspend fun update(accountId: Int, data: IncomeCategoryModel): IncomeCategoryModel
    suspend fun delete(accountId: Int, categoryId: Int): Boolean
    suspend fun existsByName(accountId: Int, name: String): Boolean
}