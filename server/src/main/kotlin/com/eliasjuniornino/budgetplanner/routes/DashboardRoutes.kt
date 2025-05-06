package com.eliasjuniornino.budgetplanner.routes

import com.eliasjuniornino.budgetplanner.controllers.dasboard.DashboardControllerImpl
import com.eliasjuniornino.budgetplanner.repositories.dashboard.DashboardRepositoryImpl
import io.ktor.server.routing.*

fun Route.getDashboardRoutes() {
    val dasboardRepository = DashboardRepositoryImpl()
    val dasboardController = DashboardControllerImpl(dasboardRepository)

    get("/dashboard/ai-resume", dasboardController::getAIResume)
    get("/dashboard/expenses-by-category", dasboardController::getExpensesByCategory)
    get("/dashboard/wallet-resume", dasboardController::getWalletResume)
}