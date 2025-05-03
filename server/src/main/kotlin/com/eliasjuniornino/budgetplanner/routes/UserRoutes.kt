package com.eliasjuniornino.budgetplanner.routes

import com.eliasjuniornino.budgetplanner.services.dashboard.DashboardService
import io.ktor.server.routing.*
import io.ktor.server.response.*

fun Route.getUserRoutes() {
  get("/user") {
    call.respond(DashboardService().getWalletResume())
  }
}