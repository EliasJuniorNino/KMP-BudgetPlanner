package com.eliasjuniornino.budgetplanner.routes

import com.eliasjuniornino.budgetplanner.dto.auth.AuthLoginDTO
import io.ktor.server.request.*
import io.ktor.server.routing.*
import com.auth0.jwt.JWT
import com.auth0.jwt.algorithms.Algorithm
import com.eliasjuniornino.budgetplanner.getAppConfigJWT
import com.eliasjuniornino.budgetplanner.repositories.users.UsersRepository
import io.ktor.server.response.*
import java.util.*

fun Route.getAuthRoutes() {
    val appConfigJWT = application.getAppConfigJWT()
    val usersRepository = UsersRepository()

    post("/auth/login") {
        val authLoginDTO = call.receive<AuthLoginDTO>()
        val user = usersRepository.findByEmailAndPassword(
            email = authLoginDTO.email,
            password = authLoginDTO.password
        )
        if (user == null) {
            call.respond(hashMapOf("message" to "User invalid"))
            return@post
        }
        val token = JWT.create()
            .withAudience(appConfigJWT.jwtAudience)
            .withIssuer(appConfigJWT.jwtIssuer)
            .withClaim("email", user.email)
            .withExpiresAt(Date(System.currentTimeMillis() + appConfigJWT.jwtTokenTime))
            .sign(Algorithm.HMAC256(appConfigJWT.jwtSecret))
        call.respond(hashMapOf("token" to token))
    }
    post("/auth/signup") {

    }
    get("/auth/logout") {

    }
}