package com.eliasjuniornino.budgetplanner.controllers.incomes

import com.eliasjuniornino.budgetplanner.dto.incomes.CreateIncomesDTO
import com.eliasjuniornino.budgetplanner.dto.incomes.UpdateIncomesDTO
import com.eliasjuniornino.budgetplanner.getAccountOrRespondError
import com.eliasjuniornino.budgetplanner.getValidIdOrRespondError
import com.eliasjuniornino.budgetplanner.models.CreateIncomeModel
import com.eliasjuniornino.budgetplanner.repositories.incomes.IncomesRepository
import io.ktor.http.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class IncomesControllerImpl(private val repository: IncomesRepository) : IncomesController {
    override suspend fun index(context: RoutingContext) = context.apply {
        val account = getAccountOrRespondError() ?: return@apply
        val incomes = repository.list(account.id)
        call.respond(incomes.map { it.toDTO() })
    }

    override suspend fun view(context: RoutingContext) = context.apply {
        val id = call.getValidIdOrRespondError() ?: return@apply
        val account = getAccountOrRespondError() ?: return@apply

        val income = repository.get(account.id, id)
        if (income == null) {
            call.respond(HttpStatusCode.NotFound, mapOf("message" to "Income not found"))
        } else {
            call.respond(income.toDTO())
        }
    }

    override suspend fun create(context: RoutingContext) = context.apply {
        val request = call.receive<CreateIncomesDTO>()
        if (!request.validate()) {
            call.respond(HttpStatusCode.BadRequest, mapOf("message" to "Invalid income"))
            return@apply
        }

        val account = getAccountOrRespondError() ?: return@apply

        if (repository.existsByName(account.id, request.name)) {
            call.respond(HttpStatusCode.Conflict, mapOf("message" to "Income with this name already exists"))
            return@apply
        }

        val now = LocalDateTime.now()
        val formatter = DateTimeFormatter.ISO_LOCAL_DATE_TIME

        val newIncome = CreateIncomeModel(
            name = request.name,
            description = request.description,
            categoryId = request.categoryId,
            subCategoryId = request.subCategoryId,
            date = request.date?.let { LocalDateTime.parse(it, formatter) } ?: now,
            accountId = account.id
        ).apply {
            request.incomeType?.let { incomeType = it }
            request.value?.let { value = it }
            request.valueMultiplier?.let { valueMultiplier = it }
            request.isDone?.let { isDone = it }
        }

        val createdIncome = repository.store(account.id, newIncome)
        call.respond(HttpStatusCode.Created, createdIncome.toDTO())
    }

    override suspend fun update(context: RoutingContext) = context.apply {
        val request = call.receive<UpdateIncomesDTO>()

        if (!request.validate()) {
            call.respond(HttpStatusCode.BadRequest, mapOf("message" to "Invalid income"))
            return@apply
        }

        val account = getAccountOrRespondError() ?: return@apply

        val income = repository.get(account.id, request.id)
        if (income == null) {
            call.respond(HttpStatusCode.NotFound, mapOf("message" to "Income not found"))
            return@apply
        }

        val formatter = DateTimeFormatter.ISO_LOCAL_DATE_TIME

        income.apply {
            request.name?.let { name = it }
            request.value?.let { value = it }
            request.valueMultiplier?.let { valueMultiplier = it }
            request.categoryId?.let { categoryId = it }
            request.subCategoryId?.let { subCategoryId = it }
            request.description?.let { description = it }
            request.isDone?.let { isDone = it }
            request.date?.let { date = LocalDateTime.parse(it, formatter) }
        }

        val updatedIncome = repository.update(account.id, income)
        call.respond(updatedIncome.toDTO())
    }

    override suspend fun delete(context: RoutingContext) = context.apply {
        val id = call.getValidIdOrRespondError() ?: return@apply
        val account = getAccountOrRespondError() ?: return@apply

        val income = repository.get(account.id, id)
        if (income == null) {
            call.respond(HttpStatusCode.NotFound, mapOf("message" to "Income not found"))
            return@apply
        }

        val deleted = repository.delete(account.id, id)
        if (!deleted) {
            call.respond(HttpStatusCode.InternalServerError, mapOf("message" to "Error deleting income"))
        } else {
            call.respond(mapOf("message" to "Income removed"))
        }
    }
}
