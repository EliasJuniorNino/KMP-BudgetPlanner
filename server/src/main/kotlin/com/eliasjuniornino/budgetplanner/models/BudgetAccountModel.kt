package com.eliasjuniornino.budgetplanner.models

data class BudgetAccountModel(
    val id: Int,
    var name: String
) {
    fun toDTO() = {}
}