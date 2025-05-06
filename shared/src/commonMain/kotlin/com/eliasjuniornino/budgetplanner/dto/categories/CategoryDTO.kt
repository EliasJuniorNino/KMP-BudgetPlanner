package com.eliasjuniornino.budgetplanner.dto.categories

import kotlinx.serialization.Serializable

@Serializable
data class CategoryDTO(
    val id: Int,
    val name: String,
    val color: String? = null,
    val icon: String? = null,
    val parentId: Int? = null,
    val createdAt: String,
    val updatedAt: String
)

