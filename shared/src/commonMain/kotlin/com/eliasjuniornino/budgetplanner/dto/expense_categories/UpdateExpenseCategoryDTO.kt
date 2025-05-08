package com.eliasjuniornino.budgetplanner.dto.expense_categories

import kotlinx.serialization.Serializable

@Serializable
data class UpdateExpenseCategoryDTO(
    val id: Int,
    val name: String? = null,
    val color: String? = null,
    val icon: String? = null,
    val parentId: Int? = null
) {
    fun validate(): Boolean {
        return id >= 0
    }
}

