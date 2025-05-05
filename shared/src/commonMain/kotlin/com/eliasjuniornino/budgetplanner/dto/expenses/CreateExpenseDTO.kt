package com.eliasjuniornino.budgetplanner.dto.expenses

import kotlinx.serialization.Serializable

@Serializable
data class CreateExpenseDTO(
    val name: String,
    val expenseType: ExpenseType = ExpenseType.UNIQUE,
    val value: Double = 0.0,
    val valueMultiplier: Double = 1.0,
    val categoryId: Int? = null,
    val subCategoryId: Int? = null,
    val isEssential: Boolean = false,
    val description: String? = null,
    val parcelCurrent: Int? = null,
    val parcelTotal: Int? = null,
    val frequencyType: FrequencyType? = null,
    val frequencyValue: String? = null,
    val isDone: Boolean = false,
    val date: String? = null,
    val dateStart: String? = null,
) {
    fun validate(): Boolean {
        return name.isNotBlank()
    }
}
