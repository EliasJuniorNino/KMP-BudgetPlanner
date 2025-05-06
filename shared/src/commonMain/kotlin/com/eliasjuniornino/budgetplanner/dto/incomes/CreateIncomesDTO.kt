package com.eliasjuniornino.budgetplanner.dto.incomes

import kotlinx.serialization.Serializable

@Serializable
data class CreateIncomesDTO(
    val name: String,
    val incomeType: IncomeType? = null,
    val value: Double? = null,
    val valueMultiplier: Double? = null,
    val categoryId: Int? = null,
    val subCategoryId: Int? = null,
    val description: String? = null,
    val isDone: Boolean? = null,
    val date: String? = null
) {
    fun validate(): Boolean {
        return name.isNotBlank()
    }
}
