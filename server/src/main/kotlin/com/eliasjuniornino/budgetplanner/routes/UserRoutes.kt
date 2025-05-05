package com.eliasjuniornino.budgetplanner.routes

import com.eliasjuniornino.budgetplanner.getAuthenticatedUserOrRespondError
import io.ktor.server.routing.*
import io.ktor.server.response.*

fun Route.getUserRoutes() {
    get("/user") {
        val user = getAuthenticatedUserOrRespondError() ?: return@get
        call.respond(user)
    }
}