package com.eliasjuniornino.budgetplanner.routes

import com.eliasjuniornino.budgetplanner.controllers.dasboard.DashboardControllerImpl
import com.eliasjuniornino.budgetplanner.repositories.dashboard.DashboardRepository
import io.ktor.server.routing.*
import io.ktor.server.response.*

fun Route.getDashboardRoutes() {
    get("/dashboard/wallet-resume") {
        call.respond(DashboardControllerImpl(DashboardRepository()).getDashboardResume())
    }
}