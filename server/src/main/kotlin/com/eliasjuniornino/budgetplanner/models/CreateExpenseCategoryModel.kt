package com.eliasjuniornino.budgetplanner.models

data class CreateExpenseCategoryModel(
    var userId: Int,
    var name: String,
    var color: String? = null,
    var icon: String? = null,
    var parentId: Int? = null,
)