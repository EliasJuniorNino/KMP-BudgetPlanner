package com.eliasjuniornino.budgetplanner.models

data class CreateIncomeCategoryModel(
    var accountId: Int,
    var name: String,
    var color: String? = null,
    var icon: String? = null,
    var parentId: Int? = null,
)