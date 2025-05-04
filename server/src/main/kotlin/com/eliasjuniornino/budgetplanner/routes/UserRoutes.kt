package com.eliasjuniornino.budgetplanner.routes

import com.eliasjuniornino.budgetplanner.dto.user.UserResponseDTO
import io.ktor.http.*
import io.ktor.server.auth.*
import io.ktor.server.auth.jwt.*
import io.ktor.server.routing.*
import io.ktor.server.response.*

fun Route.getUserRoutes() {
    get("/user") {
        val principal = call.principal<JWTPrincipal>()
        if (principal == null) {
            call.respond(HttpStatusCode.InternalServerError, mapOf("message" to "Internal server error"))
            return@get
        }
        
        val user = UserResponseDTO("name", principal.payload.getClaim("email").asString())
        call.respond(user)
    }
}