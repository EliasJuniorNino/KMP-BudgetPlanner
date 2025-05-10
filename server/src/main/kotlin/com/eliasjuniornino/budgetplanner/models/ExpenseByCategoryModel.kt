package com.eliasjuniornino.budgetplanner.models

import com.eliasjuniornino.budgetplanner.dto.dashboard.ExpenseByCategoryDTO

data class ExpenseByCategoryModel(
    val categoryName: String,
    val categoryId: Int,
    val total: Double,
    val percent: Double,
) {
    fun toDTO() = ExpenseByCategoryDTO(
        categoryName,
        categoryId,
        total,
        percent
    )
}