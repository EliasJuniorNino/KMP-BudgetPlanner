package com.eliasjuniornino.budgetplanner.dto.categories

import kotlinx.serialization.Serializable

@Serializable
data class UpdateCategoryDTO(
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

