package com.eliasjuniornino.budgetplanner.controllers.dasboard

import com.eliasjuniornino.budgetplanner.dto.dashboard.ResumeWalletReturnDTO
import com.eliasjuniornino.budgetplanner.repositories.dashboard.DashboardRepository

class DashboardController(
    private val dashboardService: DashboardRepository
) {
    fun getDashboardResume(): ResumeWalletReturnDTO {
        return ResumeWalletReturnDTO(
            walletResume = dashboardService.getWalletResume(),
            expensesByCategory = dashboardService.getExpensesByCategory(),
            aiResume = dashboardService.getWalletAiResume(),
        )
    }
}