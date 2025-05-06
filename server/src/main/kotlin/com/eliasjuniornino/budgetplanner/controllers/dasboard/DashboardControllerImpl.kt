package com.eliasjuniornino.budgetplanner.controllers.dasboard

import com.eliasjuniornino.budgetplanner.getAuthenticatedUserOrRespondError
import com.eliasjuniornino.budgetplanner.repositories.dashboard.DashboardRepository
import io.ktor.server.routing.*
import io.ktor.server.response.*

class DashboardControllerImpl(
    private val repository: DashboardRepository
) : DashboardController {
    override suspend fun getAIResume(context: RoutingContext) = context.apply {
        val user = getAuthenticatedUserOrRespondError() ?: return@apply
        val aiResume = repository.getAiResume(user.id)
        call.respond(aiResume.toDTO())
    }

    override suspend fun getExpensesByCategory(context: RoutingContext) = context.apply {
        val user = getAuthenticatedUserOrRespondError() ?: return@apply
        val expensesByCategory = repository.getExpensesByCategory(user.id)
        call.respond(expensesByCategory.map { it.toDTO() })
    }

    override suspend fun getWalletResume(context: RoutingContext) = context.apply {
        val user = getAuthenticatedUserOrRespondError() ?: return@apply
        val walletResume = repository.getWalletResume(user.id)
        call.respond(walletResume.toDTO())
    }

}