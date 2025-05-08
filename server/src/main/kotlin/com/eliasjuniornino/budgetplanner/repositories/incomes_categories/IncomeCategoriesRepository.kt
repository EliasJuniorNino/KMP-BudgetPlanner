package com.eliasjuniornino.budgetplanner.repositories.incomes_categories

import com.eliasjuniornino.budgetplanner.models.IncomeCategoryModel
import com.eliasjuniornino.budgetplanner.models.CreateIncomeCategoryModel

interface IncomeCategoriesRepository {
    suspend fun list(userId: Int): List<IncomeCategoryModel>
    suspend fun list(userId: Int, parentCategoryId: Int): List<IncomeCategoryModel>
    suspend fun store(userId: Int, data: CreateIncomeCategoryModel): IncomeCategoryModel
    suspend fun get(userId: Int, categoryId: Int): IncomeCategoryModel?
    suspend fun update(userId: Int, data: IncomeCategoryModel): IncomeCategoryModel
    suspend fun delete(userId: Int, categoryId: Int): Boolean
    suspend fun existsByName(userId: Int, name: String): Boolean
}