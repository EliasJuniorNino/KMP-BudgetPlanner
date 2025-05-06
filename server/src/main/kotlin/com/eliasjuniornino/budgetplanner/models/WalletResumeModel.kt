package com.eliasjuniornino.budgetplanner.models

import com.eliasjuniornino.budgetplanner.dto.dashboard.WalletResumeDTO

data class WalletResumeModel(
    var totalExpenses: Double,
    var totalIncomes: Double
) {
    fun toDTO() = WalletResumeDTO(
        totalExpenses, totalIncomes
    )
}