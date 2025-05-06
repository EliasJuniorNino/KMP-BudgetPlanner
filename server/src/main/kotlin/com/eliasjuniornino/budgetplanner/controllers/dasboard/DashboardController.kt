package com.eliasjuniornino.budgetplanner.controllers.dasboard

import io.ktor.server.routing.*

interface DashboardController {
    suspend fun getAIResume(context: RoutingContext): RoutingContext
    suspend fun getExpensesByCategory(context: RoutingContext): RoutingContext
    suspend fun getWalletResume(context: RoutingContext): RoutingContext
}