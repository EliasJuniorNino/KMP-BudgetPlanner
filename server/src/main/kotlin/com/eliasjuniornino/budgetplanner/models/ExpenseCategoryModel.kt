package com.eliasjuniornino.budgetplanner.models

import com.eliasjuniornino.budgetplanner.dto.expense_categories.ExpenseCategoryDTO
import com.eliasjuniornino.budgetplanner.utils.getCurrentLocalDateTime
import java.time.LocalDateTime

data class ExpenseCategoryModel(
    var accountId: Int,
    val id: Int,
    var name: String,
    var color: String? = null,
    var icon: String? = null,
    var parentId: Int? = null,
    var createdAt: LocalDateTime = getCurrentLocalDateTime(),
    var updatedAt: LocalDateTime = getCurrentLocalDateTime(),
) {
    fun toDTO() = ExpenseCategoryDTO(
        id, name, color, icon, parentId,
        createdAt = createdAt.toString(),
        updatedAt = updatedAt.toString()
    )
}