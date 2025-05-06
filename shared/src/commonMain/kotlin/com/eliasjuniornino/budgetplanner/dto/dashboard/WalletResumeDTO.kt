package com.eliasjuniornino.budgetplanner.dto.dashboard

import kotlinx.serialization.Serializable

@Serializable
data class WalletResumeDTO(
    val totalExpenses: Double,
    val totalIncomes: Double,
)