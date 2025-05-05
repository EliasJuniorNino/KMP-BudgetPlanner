package com.eliasjuniornino.budgetplanner.dto.expenses

import kotlinx.serialization.Serializable

@Serializable
data class ExpenseDTO(
    val id: Int,
    val userId: Int,
    val name: String,
    val expenseType: ExpenseType,
    val value: Double,
    val valueMultiplier: Double,
    val categoryId: Int? = null,
    val subCategoryId: Int? = null,
    val isEssential: Boolean,
    val description: String? = null,
    val parcelCurrent: Int? = null,
    val parcelTotal: Int? = null,
    val frequencyType: FrequencyType? = null,
    val frequencyValue: String? = null,
    val isDone: Boolean,
    val date: String,
    val dateStart: String,
    val createdAt: String,
    val updatedAt: String,
)

