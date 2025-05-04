package com.eliasjuniornino.budgetplanner

import com.auth0.jwt.JWT
import com.auth0.jwt.algorithms.Algorithm
import com.eliasjuniornino.budgetplanner.dto.auth.AuthLoginDTO
import com.eliasjuniornino.budgetplanner.routes.*
import io.ktor.http.*
import kotlinx.serialization.json.Json
import io.ktor.server.application.*
import io.ktor.server.engine.*
import io.ktor.server.netty.*
import io.ktor.server.routing.*
import io.ktor.server.auth.*
import io.ktor.server.auth.jwt.*
import io.ktor.server.plugins.contentnegotiation.ContentNegotiation
import io.ktor.serialization.kotlinx.json.*
import io.ktor.server.request.receive
import io.ktor.server.response.*
import java.util.Date

fun Application.getEnv(name: String, defaultValue: String): String {
    return environment.config.propertyOrNull(name)?.getString() ?: defaultValue
}

fun main() {
    embeddedServer(Netty, port = SERVER_PORT, host = "0.0.0.0", module = Application::module)
        .start(wait = true)
}

fun Application.module() {
    val secret = getEnv("jwt.secret", "secret")
    val issuer = getEnv("jwt.issuer", "http://0.0.0.0:${SERVER_PORT}/")
    val audience = getEnv("jwt.audience", "http://0.0.0.0:${SERVER_PORT}/hello")
    val myRealm = getEnv("jwt.realm", "Access to 'hello'")

    configureDatabase()

    install(ContentNegotiation) {
        json(Json {
            prettyPrint = true
            isLenient = true
        })
    }
    install(Authentication) {
        jwt("auth-jwt") {
            realm = myRealm
            verifier(
                JWT
                    .require(Algorithm.HMAC256(secret))
                    .withAudience(audience)
                    .withIssuer(issuer)
                    .build()
            )
            validate { credential ->
                if (credential.payload.getClaim("email").asString() != "") {
                    JWTPrincipal(credential.payload)
                } else {
                    null
                }
            }
            challenge { defaultScheme, realm ->
                call.respond(
                    HttpStatusCode.Unauthorized,
                    hashMapOf("message" to "Token is not valid or has expired")
                )
            }
        }
    }

    routing {
        route("api/v1") {
            post("/auth/login") {
                val user = call.receive<AuthLoginDTO>()
                val token = JWT.create()
                    .withAudience(audience)
                    .withIssuer(issuer)
                    .withClaim("email", user.email)
                    .withExpiresAt(Date(System.currentTimeMillis() + 60000))
                    .sign(Algorithm.HMAC256(secret))
                call.respond(hashMapOf("token" to token))
            }
            post("/auth/signup") {

            }
            get("/auth/logout") {

            }
            authenticate("auth-jwt") {
                getDashboardRoutes()
                getExpensesRoutes()
                getIncomesRoutes()
                getUserRoutes()
            }
        }
    }
}