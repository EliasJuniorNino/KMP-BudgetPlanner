package com.eliasjuniornino.budgetplanner.controllers

import com.eliasjuniornino.budgetplanner.dto.dashboard.ResumeWalletReturnDTO
import com.eliasjuniornino.budgetplanner.services.dashboard.DashboardService

class DashboardController(
    private val dashboardService: DashboardService
) {
    fun getDashboardResume(): ResumeWalletReturnDTO {
        return ResumeWalletReturnDTO(
            walletResume = dashboardService.getWalletResume(),
            expensesByCategory = dashboardService.getExpensesByCategory(),
            aiResume = dashboardService.getWalletAiResume(),
        )
    }
}