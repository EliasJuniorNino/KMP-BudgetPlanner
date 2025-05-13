package com.eliasjuniornino.budgetplanner.controllers.accounts

import com.eliasjuniornino.budgetplanner.dto.accounts.CreateAccountDTO
import com.eliasjuniornino.budgetplanner.dto.accounts.UpdateAccountDTO
import com.eliasjuniornino.budgetplanner.getAuthenticatedUserOrRespondError
import com.eliasjuniornino.budgetplanner.getValidIdOrRespondError
import com.eliasjuniornino.budgetplanner.models.CreateAccountModel
import com.eliasjuniornino.budgetplanner.repositories.accounts.AccountsRepository
import io.ktor.http.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

class AccountControllerImpl(private val repository: AccountsRepository) : AccountController {
    override suspend fun index(context: RoutingContext) = context.apply {
        val user = getAuthenticatedUserOrRespondError() ?: return@apply
        val accounts = repository.list(user.id)
        call.respond(accounts.map { it.toDTO() })
    }

    override suspend fun view(context: RoutingContext) = context.apply {
        val id = call.getValidIdOrRespondError() ?: return@apply
        val user = getAuthenticatedUserOrRespondError() ?: return@apply

        val account = repository.get(user.id, id)
        if (account == null) {
            call.respond(HttpStatusCode.NotFound, mapOf("message" to "Account not found"))
        } else {
            call.respond(account.toDTO())
        }
    }

    override suspend fun create(context: RoutingContext) = context.apply {
        val request = call.receive<CreateAccountDTO>()
        if (!request.validate()) {
            call.respond(HttpStatusCode.BadRequest, mapOf("message" to "Invalid account"))
            return@apply
        }

        val user = getAuthenticatedUserOrRespondError() ?: return@apply

        if (repository.existsByName(user.id, request.name)) {
            call.respond(HttpStatusCode.Conflict, mapOf("message" to "Account with this name already exists"))
            return@apply
        }

        val newAccount = CreateAccountModel(
            name = request.name
        )

        val createdAccount = repository.store(user.id, newAccount)
        call.respond(HttpStatusCode.Created, createdAccount.toDTO())
    }

    override suspend fun update(context: RoutingContext) = context.apply {
        val request = call.receive<UpdateAccountDTO>()

        if (!request.validate()) {
            call.respond(HttpStatusCode.BadRequest, mapOf("message" to "Invalid account"))
            return@apply
        }

        val user = getAuthenticatedUserOrRespondError() ?: return@apply

        val account = repository.get(user.id, request.id)
        if (account == null) {
            call.respond(HttpStatusCode.NotFound, mapOf("message" to "Account not found"))
            return@apply
        }

        account.apply {
            name = request.name
        }

        val updatedAccount = repository.update(user.id, account)
        call.respond(updatedAccount.toDTO())
    }

    override suspend fun delete(context: RoutingContext) = context.apply {
        val id = call.getValidIdOrRespondError() ?: return@apply
        val user = getAuthenticatedUserOrRespondError() ?: return@apply

        val account = repository.get(user.id, id)
        if (account == null) {
            call.respond(HttpStatusCode.NotFound, mapOf("message" to "Account not found"))
            return@apply
        }

        val deleted = repository.delete(user.id, id)
        if (!deleted) {
            call.respond(HttpStatusCode.InternalServerError, mapOf("message" to "Error deleting account"))
        } else {
            call.respond(mapOf("message" to "Account removed"))
        }
    }
}
