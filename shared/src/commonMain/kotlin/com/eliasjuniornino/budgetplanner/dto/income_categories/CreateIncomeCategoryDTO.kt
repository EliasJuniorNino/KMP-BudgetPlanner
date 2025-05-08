package com.eliasjuniornino.budgetplanner.dto.income_categories

import kotlinx.serialization.Serializable

@Serializable
data class CreateIncomeCategoryDTO(
    val name: String? = null,
    val color: String? = null,
    val icon: String? = null,
    val parentId: Int? = null
) {
    fun validate(): Boolean {
        return !name.isNullOrBlank()
    }
}

