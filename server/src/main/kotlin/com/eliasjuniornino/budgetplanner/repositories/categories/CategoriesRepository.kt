package com.eliasjuniornino.budgetplanner.repositories.categories

import com.eliasjuniornino.budgetplanner.models.CategoryModel
import com.eliasjuniornino.budgetplanner.models.CreateCategoryModel

interface CategoriesRepository {
    suspend fun list(userId: Int): List<CategoryModel>
    suspend fun list(userId: Int, parentCategoryId: Int): List<CategoryModel>
    suspend fun store(userId: Int, data: CreateCategoryModel): CategoryModel
    suspend fun get(userId: Int, categoryId: Int): CategoryModel?
    suspend fun update(userId: Int, data: CategoryModel): CategoryModel
    suspend fun delete(userId: Int, categoryId: Int): Boolean
    suspend fun existsByName(userId: Int, name: String): Boolean
}