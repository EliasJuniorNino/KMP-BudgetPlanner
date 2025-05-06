package com.eliasjuniornino.budgetplanner.dto.expenses

import kotlinx.serialization.Serializable

@Serializable
data class UpdateExpenseDTO(
    val id: Int,
    val name: String? = null,
    val expenseType: ExpenseType? = null,
    val value: Double? = null,
    val valueMultiplier: Double? = null,
    val categoryId: Int? = null,
    val subCategoryId: Int? = null,
    val isEssential: Boolean? = null,
    val description: String? = null,
    val parcelCurrent: Int? = null,
    val parcelTotal: Int? = null,
    val frequencyType: FrequencyType? = null,
    val frequencyValue: String? = null,
    val isDone: Boolean? = null,
    val date: String? = null,
    val dateStart: String? = null,
) {
    fun validate(): Boolean {
        return id >= 0
    }
}
