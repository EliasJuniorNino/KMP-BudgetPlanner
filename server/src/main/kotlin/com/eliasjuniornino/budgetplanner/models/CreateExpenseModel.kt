package com.eliasjuniornino.budgetplanner.models

import com.eliasjuniornino.budgetplanner.dto.expenses.ExpenseType
import com.eliasjuniornino.budgetplanner.dto.expenses.FrequencyType
import com.eliasjuniornino.budgetplanner.utils.getCurrentLocalDateTime
import java.time.LocalDateTime

data class CreateExpenseModel(
    var accountId: Int,
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
)