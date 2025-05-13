package com.eliasjuniornino.budgetplanner.routes

import com.eliasjuniornino.budgetplanner.controllers.dasboard.DashboardControllerImpl
import com.eliasjuniornino.budgetplanner.repositories.dashboard.DashboardRepositoryImpl
import io.ktor.server.routing.*

fun Route.getDashboardRoutes() {
    val dashboardRepository = DashboardRepositoryImpl()
    val dashboardController = DashboardControllerImpl(dashboardRepository)

    get("/dashboard/ai-resume", dashboardController::getAIResume)
    get("/dashboard/expenses-by-category", dashboardController::getExpensesByCategory)
    get("/dashboard/wallet-resume", dashboardController::getWalletResume)
}
