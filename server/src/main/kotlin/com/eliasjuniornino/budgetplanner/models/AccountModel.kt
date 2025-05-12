package com.eliasjuniornino.budgetplanner.models

data class AccountModel(
    val id: Int,
    var name: String,
    var userId: Int
) {
    fun toDTO() = {}
}