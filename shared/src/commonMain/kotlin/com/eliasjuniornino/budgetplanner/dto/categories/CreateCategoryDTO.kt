package com.eliasjuniornino.budgetplanner.dto.categories

import kotlinx.serialization.Serializable

@Serializable
data class UpdateCategoryDTO(
    val id: Int,
    val name: String? = null,
    val color: String? = null,
    val icon: String? = null,
    val parentId: Long? = null,
) {
    fun sanitize() = UpdateCategoryDTO(
        id = id,
        name = name?.substring(255),
        color = color?.substring(9),
        icon = icon?.substring(255),
        parentId = parentId
    )

    fun validate(): Boolean {
        if (name.isNullOrBlank()) return false
        if (color.isNullOrBlank()) return false
        if (icon.isNullOrBlank()) return false
        return true
    }
}

