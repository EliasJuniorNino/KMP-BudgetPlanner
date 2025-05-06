package com.eliasjuniornino.budgetplanner.dto.incomes

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class IncomesDTO(
    val id: Int,
    val name: String,
    val incomeType: IncomeType,
    val value: Double,
    val valueMultiplier: Double,
    val categoryId: Int? = null,
    val subCategoryId: Int? = null,
    val description: String? = null,
    val isDone: Boolean,
    val date: String,
    val createdAt: String,
    val updatedAt: String
)

@Serializable
enum class IncomeType {
    @SerialName("simple")
    SIMPLE
}