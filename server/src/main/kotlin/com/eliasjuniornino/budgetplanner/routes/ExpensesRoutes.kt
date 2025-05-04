package com.eliasjuniornino.budgetplanner.routes

import com.eliasjuniornino.budgetplanner.services.dashboard.DashboardService
import io.ktor.server.routing.*
import io.ktor.server.response.*

fun Route.getExpensesRoutes() {
    get("/expenses") {
        call.respond(DashboardService().getWalletResume())
    }
    post("/expenses") {
        call.respond(DashboardService().getWalletResume())
    }
    get("/expenses/{id}") {
        call.respond(DashboardService().getWalletResume())
    }
    delete("/expenses/{id}") {
        call.respond(DashboardService().getWalletResume())
    }
}