package com.eliasjuniornino.budgetplanner.models

import com.eliasjuniornino.budgetplanner.dto.incomes.IncomeType
import com.eliasjuniornino.budgetplanner.dto.incomes.IncomeDTO
import java.time.LocalDateTime

data class IncomeModel(
    val id: Int = 0,
    val userId: Int,
    var name: String,
    var incomeType: IncomeType = IncomeType.SIMPLE,
    var value: Double = 0.0,
    var valueMultiplier: Double = 1.0,
    var categoryId: Int? = null,
    var subCategoryId: Int? = null,
    var description: String? = null,
    var isDone: Boolean = false,
    var date: LocalDateTime,
    val createdAt: LocalDateTime,
    var updatedAt: LocalDateTime,
) {
    fun toDTO() = IncomeDTO(
        id = id,
        name = name,
        incomeType = incomeType,
        value = value,
        valueMultiplier = valueMultiplier,
        categoryId = categoryId,
        subCategoryId = subCategoryId,
        description = description,
        isDone = isDone,
        date = date.toString(),
        createdAt = createdAt.toString(),
        updatedAt = updatedAt.toString()
    )
}