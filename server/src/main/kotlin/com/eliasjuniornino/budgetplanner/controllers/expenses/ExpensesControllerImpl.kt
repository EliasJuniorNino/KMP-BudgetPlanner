package com.eliasjuniornino.budgetplanner.controllers.expenses

import com.eliasjuniornino.budgetplanner.dto.expenses.CreateExpenseDTO
import com.eliasjuniornino.budgetplanner.dto.expenses.UpdateExpenseDTO
import com.eliasjuniornino.budgetplanner.getAuthenticatedUserOrRespondError
import com.eliasjuniornino.budgetplanner.models.CreateExpenseModel
import com.eliasjuniornino.budgetplanner.repositories.expenses.ExpensesRepository
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

private suspend fun ApplicationCall.getValidIdOrRespondError(): Int? {
    return parameters["id"]?.toIntOrNull() ?: run {
        respond(HttpStatusCode.BadRequest, mapOf("message" to "Invalid or missing ID"))
        null
    }
}

class ExpensesControllerImpl(private val repository: ExpensesRepository) : ExpensesController {
    override suspend fun index(context: RoutingContext) = context.apply {
        val user = getAuthenticatedUserOrRespondError() ?: return@apply
        val expenses = repository.list(user.id)
        call.respond(expenses.map { it.toDTO() })
    }

    override suspend fun view(context: RoutingContext) = context.apply {
        val id = call.getValidIdOrRespondError() ?: return@apply
        val user = getAuthenticatedUserOrRespondError() ?: return@apply

        val expense = repository.get(user.id, id)
        if (expense == null) {
            call.respond(HttpStatusCode.NotFound, mapOf("message" to "Expense not found"))
        } else {
            call.respond(expense.toDTO())
        }
    }

    override suspend fun create(context: RoutingContext) = context.apply {
        val request = call.receive<CreateExpenseDTO>()
        if (!request.validate()) {
            call.respond(HttpStatusCode.BadRequest, mapOf("message" to "Invalid expense"))
            return@apply
        }

        val user = getAuthenticatedUserOrRespondError() ?: return@apply

        if (repository.existsByName(user.id, request.name)) {
            call.respond(HttpStatusCode.Conflict, mapOf("message" to "Expense with this name already exists"))
            return@apply
        }

        val now = LocalDateTime.now()
        val formatter = DateTimeFormatter.ISO_LOCAL_DATE_TIME

        val newExpense = CreateExpenseModel(
            accountId = user.id,
            name = request.name,
            expenseType = request.expenseType,
            value = request.value,
            valueMultiplier = request.valueMultiplier,
            categoryId = request.categoryId,
            subCategoryId = request.subCategoryId,
            isEssential = request.isEssential,
            description = request.description,
            parcelCurrent = request.parcelCurrent,
            parcelTotal = request.parcelTotal,
            frequencyType = request.frequencyType,
            frequencyValue = request.frequencyValue,
            isDone = request.isDone,
            date = request.date?.let { LocalDateTime.parse(it, formatter) } ?: now,
            dateStart = request.dateStart?.let { LocalDateTime.parse(it, formatter) } ?: now,
            createdAt = now,
            updatedAt = now,
        )

        val createdExpense = repository.store(user.id, newExpense)
        call.respond(HttpStatusCode.Created, createdExpense.toDTO())
    }

    override suspend fun update(context: RoutingContext) = context.apply {
        val request = call.receive<UpdateExpenseDTO>()

        if (!request.validate()) {
            call.respond(HttpStatusCode.BadRequest, mapOf("message" to "Invalid expense"))
            return@apply
        }

        val user = getAuthenticatedUserOrRespondError() ?: return@apply

        val expense = repository.get(user.id, request.id)
        if (expense == null) {
            call.respond(HttpStatusCode.NotFound, mapOf("message" to "Expense not found"))
            return@apply
        }

        val formatter = DateTimeFormatter.ISO_LOCAL_DATE_TIME

        expense.apply {
            request.name?.let { name = it }
            request.expenseType?.let { expenseType = it }
            request.value?.let { value = it }
            request.valueMultiplier?.let { valueMultiplier = it }
            request.categoryId?.let { categoryId = it }
            request.subCategoryId?.let { subCategoryId = it }
            request.isEssential?.let { isEssential = it }
            request.description?.let { description = it }
            request.parcelCurrent?.let { parcelCurrent = it }
            request.parcelTotal?.let { parcelTotal = it }
            request.frequencyType?.let { frequencyType = it }
            request.frequencyValue?.let { frequencyValue = it }
            request.isDone?.let { isDone = it }
            request.date?.let { date = LocalDateTime.parse(it, formatter) }
            request.dateStart?.let { dateStart = LocalDateTime.parse(it, formatter) }
            updatedAt = LocalDateTime.now()
        }

        val updatedExpense = repository.update(user.id, expense)
        call.respond(updatedExpense.toDTO())
    }

    override suspend fun delete(context: RoutingContext) = context.apply {
        val id = call.getValidIdOrRespondError() ?: return@apply
        val user = getAuthenticatedUserOrRespondError() ?: return@apply

        val expense = repository.get(user.id, id)
        if (expense == null) {
            call.respond(HttpStatusCode.NotFound, mapOf("message" to "Expense not found"))
            return@apply
        }

        val deleted = repository.delete(user.id, id)
        if (!deleted) {
            call.respond(HttpStatusCode.InternalServerError, mapOf("message" to "Error deleting expense"))
        } else {
            call.respond(mapOf("message" to "Expense removed"))
        }
    }
}
