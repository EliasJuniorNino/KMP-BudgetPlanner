package com.eliasjuniornino.budgetplanner.models

data class CreateUserModel(
    var name: String,
    val email: String,
    var password: String
)