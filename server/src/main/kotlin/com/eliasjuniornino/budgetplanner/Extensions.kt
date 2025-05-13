package com.eliasjuniornino.budgetplanner

import com.eliasjuniornino.budgetplanner.dao.AccountDAO
import com.eliasjuniornino.budgetplanner.dao.daoToModel
import com.eliasjuniornino.budgetplanner.models.AccountModel
import com.eliasjuniornino.budgetplanner.models.UserModel
import io.ktor.server.routing.*
import io.ktor.http.*
import io.ktor.server.application.ApplicationCall
import io.ktor.server.response.*
import kotlinx.coroutines.Dispatchers
import org.jetbrains.exposed.sql.transactions.experimental.newSuspendedTransaction

fun RoutingContext.getAuthenticatedUser(): UserModel? {
    return try {
        call.attributes[AUTHENTICATED_USER_KEY]
    } catch (e: NoSuchElementException) {
        null
    }
}

suspend fun RoutingContext.getAuthenticatedUserOrRespondError(message: String? = null): UserModel? {
    val user = getAuthenticatedUser()
    if (user == null) {
        call.respond(HttpStatusCode.Unauthorized, mapOf("message" to (message ?: "Unauthenticated user")))
    }
    return user
}

suspend fun RoutingContext.getAccountOrRespondError(message: String? = null): AccountModel? {
    val accountIdHeader = call.request.headers["account_id"]
    val accountId = accountIdHeader?.toIntOrNull()

    if (accountId == null) {
        call.respond(HttpStatusCode.BadRequest, mapOf("message" to (message ?: "Invalid or missing account_id header")))
        return null
    }

    val account = newSuspendedTransaction(Dispatchers.IO) {
        val account = AccountDAO.findById(accountId) ?: return@newSuspendedTransaction null
        return@newSuspendedTransaction daoToModel(account)
    }

    if (account == null) {
        call.respond(HttpStatusCode.NotFound, mapOf("message" to (message ?: "Account not found")))
        return null
    }

    return account
}

suspend fun ApplicationCall.getValidIdOrRespondError(message: String? = null): Int? {
    return parameters["id"]?.toIntOrNull() ?: run {
        respond(HttpStatusCode.BadRequest, mapOf("message" to (message ?: "Invalid or missing ID")))
        null
    }
}