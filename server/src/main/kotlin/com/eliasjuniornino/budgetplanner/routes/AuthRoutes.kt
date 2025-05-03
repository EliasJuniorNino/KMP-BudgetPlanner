package com.eliasjuniornino.budgetplanner.routes

import com.eliasjuniornino.budgetplanner.services.dashboard.DashboardService
import io.ktor.server.routing.*
import io.ktor.server.response.*

fun Route.getAuthRoutes() {
  post("/login") {
    call.respond(DashboardService().getWalletResume())
  }
  get("/logout") {
    call.respond(DashboardService().getWalletResume())
  }
}