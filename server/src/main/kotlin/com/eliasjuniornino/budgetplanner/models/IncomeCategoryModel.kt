package com.eliasjuniornino.budgetplanner.models

import com.eliasjuniornino.budgetplanner.dto.income_categories.IncomeCategoryDTO
import com.eliasjuniornino.budgetplanner.utils.getCurrentLocalDateTime
import java.time.LocalDateTime

data class IncomeCategoryModel(
    var userId: Int,
    val id: Int,
    var name: String,
    var color: String? = null,
    var icon: String? = null,
    var parentId: Int? = null,
    var createdAt: LocalDateTime = getCurrentLocalDateTime(),
    var updatedAt: LocalDateTime = getCurrentLocalDateTime(),
) {
    fun toDTO() = IncomeCategoryDTO(
        id, name, color, icon, parentId,
        createdAt = createdAt.toString(),
        updatedAt = updatedAt.toString()
    )
}
