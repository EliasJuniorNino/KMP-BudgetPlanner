package com.eliasjuniornino.budgetplanner.models

import com.eliasjuniornino.budgetplanner.dto.accounts.AccountDTO

data class AccountModel(
    val id: Int,
    var name: String,
    var userId: Int
) {
    fun toDTO() = AccountDTO(
        id = id,
        name = name,
        userId = userId
    )
}