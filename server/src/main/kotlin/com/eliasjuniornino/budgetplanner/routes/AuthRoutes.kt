package com.eliasjuniornino.budgetplanner.routes

import com.eliasjuniornino.budgetplanner.dto.auth.AuthLoginDTO
import com.eliasjuniornino.budgetplanner.dto.auth.AuthSignupDTO
import com.eliasjuniornino.budgetplanner.getAppConfigJWT
import com.eliasjuniornino.budgetplanner.repositories.users.UsersRepository
import com.auth0.jwt.JWT
import com.auth0.jwt.algorithms.Algorithm
import com.eliasjuniornino.budgetplanner.models.CreateUserModel
import io.ktor.http.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import org.mindrot.jbcrypt.BCrypt
import java.util.*

fun Route.getAuthRoutes() {
    val appConfigJWT = application.getAppConfigJWT()
    val usersRepository = UsersRepository()

    route("/auth") {
        post("/login") {
            val authLoginDTO = call.receive<AuthLoginDTO>()
            val user = usersRepository.findByEmail(authLoginDTO.email)

            if (user == null || !BCrypt.checkpw(authLoginDTO.password, user.password)) {
                call.respond(mapOf("message" to "Invalid email or password"))
                return@post
            }

            val token = JWT.create()
                .withAudience(appConfigJWT.jwtAudience)
                .withIssuer(appConfigJWT.jwtIssuer)
                .withClaim("email", user.email)
                .withExpiresAt(Date(System.currentTimeMillis() + appConfigJWT.jwtTokenTime))
                .sign(Algorithm.HMAC256(appConfigJWT.jwtSecret))

            call.respond(mapOf("token" to token))
        }

        post("/signup") {
            val signupDTO = call.receive<AuthSignupDTO>()

            if (usersRepository.existsByEmail(signupDTO.email)) {
                call.respond(mapOf("message" to "Email already registered"))
                return@post
            }

            val hashedPassword = BCrypt.hashpw(signupDTO.password, BCrypt.gensalt())

            val newUser = CreateUserModel(
                name = signupDTO.name,
                email = signupDTO.email,
                password = hashedPassword
            )

            usersRepository.store(newUser)
            call.respond(HttpStatusCode.Created, mapOf("message" to "User created successfully"))
        }

        get("/logout") {
            call.respond(mapOf("message" to "Logout endpoint"))
        }
    }
}
