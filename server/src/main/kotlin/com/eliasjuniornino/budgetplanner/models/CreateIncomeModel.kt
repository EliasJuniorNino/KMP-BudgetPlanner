package com.eliasjuniornino.budgetplanner.models

import com.eliasjuniornino.budgetplanner.dto.incomes.IncomeType
import com.eliasjuniornino.budgetplanner.utils.getCurrentLocalDateTime
import java.time.LocalDateTime

data class CreateIncomeModel(
    var accountId: Int,
    var name: String,
    var incomeType: IncomeType = IncomeType.SIMPLE,
    var value: Double = 0.0,
    var valueMultiplier: Double = 1.0,
    var categoryId: Int? = null,
    var subCategoryId: Int? = null,
    var description: String? = null,
    var isDone: Boolean = false,
    var date: LocalDateTime = getCurrentLocalDateTime()
)