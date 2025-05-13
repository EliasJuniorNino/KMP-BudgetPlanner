package com.eliasjuniornino.budgetplanner.dto.accounts

import kotlinx.serialization.Serializable

@Serializable
data class AccountDTO(
    val id: Int,
    val name: String,
    val userId: Int
)