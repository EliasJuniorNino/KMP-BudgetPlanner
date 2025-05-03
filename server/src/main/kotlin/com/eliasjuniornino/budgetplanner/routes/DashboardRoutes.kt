package com.eliasjuniornino.budgetplanner.routes

import com.eliasjuniornino.budgetplanner.controllers.DashboardController
import com.eliasjuniornino.budgetplanner.services.dashboard.DashboardService
import io.ktor.server.routing.*
import io.ktor.server.response.*

fun Route.getDashboardRoutes() {
  get("/dashboard/wallet-resume") {
    call.respond(DashboardController(DashboardService()).getDashboardResume())
  }
}