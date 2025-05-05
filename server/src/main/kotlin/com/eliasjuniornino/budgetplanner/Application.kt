package com.eliasjuniornino.budgetplanner

import com.auth0.jwt.JWT
import com.auth0.jwt.algorithms.Algorithm
import com.eliasjuniornino.budgetplanner.repositories.users.UsersRepository
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
import io.ktor.server.response.*

const val SERVER_PORT = 8080

data class AppConfig(
    var jwtSecret: String,
    var jwtIssuer: String,
    var jwtAudience: String,
    var jwtRealm: String,
    var jwtTokenTime: Int
)

fun Application.getEnv(name: String, default: String): String {
    return environment.config.propertyOrNull(name)?.getString() ?: default
}

fun Application.getEnv(name: String, default: Int): Int {
    return environment.config.propertyOrNull(name)?.getString()?.toInt() ?: default
}

fun Application.getAppConfigJWT(): AppConfig {
    val jwtSecret = getEnv("jwt.secret", "secret")
    val jwtIssuer = getEnv("jwt.issuer", "http://0.0.0.0:${SERVER_PORT}/")
    val jwtAudience = getEnv("jwt.audience", "http://0.0.0.0:${SERVER_PORT}/hello")
    val jwtRealm = getEnv("jwt.realm", "Access to 'hello'")
    val jwtTokenTime = getEnv("jwt.tokenTime", 24 * 60 * 60 * 1000)
    return AppConfig(
        jwtSecret = jwtSecret,
        jwtIssuer = jwtIssuer,
        jwtAudience = jwtAudience,
        jwtRealm = jwtRealm,
        jwtTokenTime = jwtTokenTime
    )
}

fun main() {
    embeddedServer(Netty, port = SERVER_PORT, host = "0.0.0.0", module = Application::module)
        .start(wait = true)
}

fun Application.module() {
    val appConfigJWT = getAppConfigJWT()

    configureDatabase()

    val usersRepository = UsersRepository()

    install(ContentNegotiation) {
        json(Json {
            prettyPrint = true
            isLenient = true
        })
    }

    install(Authentication) {
        jwt("auth-jwt") {
            realm = appConfigJWT.jwtRealm
            verifier(
                JWT
                    .require(Algorithm.HMAC256(appConfigJWT.jwtSecret))
                    .withAudience(appConfigJWT.jwtAudience)
                    .withIssuer(appConfigJWT.jwtIssuer)
                    .build()
            )
            validate { credential ->
                val email: String? = credential.payload.getClaim("email").asString()
                if (email.isNullOrBlank()) return@validate null
                val user = usersRepository.findByEmail(email) ?: return@validate null
                attributes.put(AUTHENTICATED_USER_KEY, user)
                JWTPrincipal(credential.payload)
            }
            challenge { _, _ ->
                call.respond(
                    HttpStatusCode.Unauthorized,
                    hashMapOf("message" to "Token is not valid or has expired")
                )
            }
        }
    }

    routing {
        route("api/v1") {
            getAuthRoutes()
            authenticate("auth-jwt") {
                getCategoriesRoutes()
                getDashboardRoutes()
                getExpensesRoutes()
                getIncomesRoutes()
                getUserRoutes()
            }
        }
    }
}