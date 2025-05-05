package com.eliasjuniornino.budgetplanner.repositories.categories

import com.eliasjuniornino.budgetplanner.models.CategoryModel
import com.eliasjuniornino.budgetplanner.repositories.IBaseRepository

interface ICategoriesRepository : IBaseRepository<CategoryModel> {
    suspend fun existsByName(userId: Int, name: String): Boolean
}