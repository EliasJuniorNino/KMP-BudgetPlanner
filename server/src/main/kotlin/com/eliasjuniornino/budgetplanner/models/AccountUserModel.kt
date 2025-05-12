package com.eliasjuniornino.budgetplanner.models

data class AccountUserModel(
    val id: Int,
    val accountId: Int,
    val userId: Int,
) {
    fun toDTO() = {}
}