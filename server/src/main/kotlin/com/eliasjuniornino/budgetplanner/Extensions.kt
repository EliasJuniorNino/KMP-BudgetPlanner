package com.eliasjuniornino.budgetplanner

import com.eliasjuniornino.budgetplanner.models.UserModel
import io.ktor.server.routing.*
import io.ktor.http.*
import io.ktor.server.response.*

fun RoutingContext.getAuthenticatedUser(): UserModel? {
    return try {
        call.attributes[AUTHENTICATED_USER_KEY]
    } catch (e: NoSuchElementException) {
        null
    }
}

suspend fun RoutingContext.getAuthenticatedUserOrRespondError(): UserModel? {
    val user = getAuthenticatedUser()
    if (user == null) {
        call.respond(HttpStatusCode.Unauthorized, mapOf("message" to "Unauthenticated user"))
    }
    return user
}