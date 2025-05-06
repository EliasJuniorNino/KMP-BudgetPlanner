package com.eliasjuniornino.budgetplanner.dto.dashboard

import kotlinx.serialization.Serializable

@Serializable
data class ExpenseByCategoryDTO(
    val categoryName: String,
    val categoryId: Int,
    val total: Double,
    val percent: Double,
)