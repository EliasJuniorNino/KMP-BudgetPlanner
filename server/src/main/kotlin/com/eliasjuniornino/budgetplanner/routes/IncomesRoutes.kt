package com.eliasjuniornino.budgetplanner.routes

import com.eliasjuniornino.budgetplanner.repositories.dashboard.DashboardRepository
import io.ktor.server.routing.*
import io.ktor.server.response.*

fun Route.getIncomesRoutes() {
    get("/incomes") {
        call.respond(DashboardRepository().getWalletResume())
    }
    post("/incomes") {
        call.respond(DashboardRepository().getWalletResume())
    }
    get("/incomes/{id}") {
        call.respond(DashboardRepository().getWalletResume())
    }
    delete("/incomes/{id}") {
        call.respond(DashboardRepository().getWalletResume())
    }
}