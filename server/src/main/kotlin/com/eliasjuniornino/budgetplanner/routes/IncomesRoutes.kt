package com.eliasjuniornino.budgetplanner.routes

import com.eliasjuniornino.budgetplanner.services.dashboard.DashboardService
import io.ktor.server.routing.*
import io.ktor.server.response.*

fun Route.getIncomesRoutes() {
    get("/incomes") {
        call.respond(DashboardService().getWalletResume())
    }
    post("/incomes") {
        call.respond(DashboardService().getWalletResume())
    }
    get("/incomes/{id}") {
        call.respond(DashboardService().getWalletResume())
    }
    delete("/incomes/{id}") {
        call.respond(DashboardService().getWalletResume())
    }
}