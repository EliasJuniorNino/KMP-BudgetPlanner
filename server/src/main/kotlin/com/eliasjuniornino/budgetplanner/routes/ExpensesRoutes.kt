package com.eliasjuniornino.budgetplanner.routes

import com.eliasjuniornino.budgetplanner.repositories.dashboard.DashboardRepository
import io.ktor.server.routing.*
import io.ktor.server.response.*

fun Route.getExpensesRoutes() {
    get("/expenses") {
        call.respond(DashboardRepository().getWalletResume())
    }
    post("/expenses") {
        call.respond(DashboardRepository().getWalletResume())
    }
    get("/expenses/{id}") {
        call.respond(DashboardRepository().getWalletResume())
    }
    delete("/expenses/{id}") {
        call.respond(DashboardRepository().getWalletResume())
    }
}