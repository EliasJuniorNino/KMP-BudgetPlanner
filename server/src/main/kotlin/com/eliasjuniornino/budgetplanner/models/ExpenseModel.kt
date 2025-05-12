package com.eliasjuniornino.budgetplanner.models

import com.eliasjuniornino.budgetplanner.dto.expenses.ExpenseDTO
import com.eliasjuniornino.budgetplanner.dto.expenses.ExpenseType
import com.eliasjuniornino.budgetplanner.dto.expenses.FrequencyType
import java.time.LocalDateTime
import com.eliasjuniornino.budgetplanner.utils.getCurrentLocalDateTime

data class ExpenseModel(
    val id: Int,
    val accountId: Int,
    var name: String,
    var expenseType: ExpenseType = ExpenseType.UNIQUE,
    var value: Double = 0.0,
    var valueMultiplier: Double = 1.0,
    var categoryId: Int? = null,
    var subCategoryId: Int? = null,
    var isEssential: Boolean = false,
    var description: String? = null,
    var parcelCurrent: Int? = null,
    var parcelTotal: Int? = null,
    var frequencyType: FrequencyType? = null,
    var frequencyValue: String? = null,
    var isDone: Boolean = false,
    var date: LocalDateTime = getCurrentLocalDateTime(),
    var dateStart: LocalDateTime? = null,
    var createdAt: LocalDateTime = getCurrentLocalDateTime(),
    var updatedAt: LocalDateTime = getCurrentLocalDateTime(),
) {
    fun toDTO() = ExpenseDTO(
        id = id,
        name = name,
        expenseType = expenseType,
        value = value,
        valueMultiplier = valueMultiplier,
        categoryId = categoryId,
        subCategoryId = subCategoryId,
        isEssential = isEssential,
        description = description,
        parcelCurrent = parcelCurrent,
        parcelTotal = parcelTotal,
        frequencyType = frequencyType,
        frequencyValue = frequencyValue,
        isDone = isDone,
        date = date.toString(),
        dateStart = dateStart.toString(),
        createdAt = createdAt.toString(),
        updatedAt = updatedAt.toString()
    )
}