package com.eliasjuniornino.budgetplanner.controllers.expenses

import com.eliasjuniornino.budgetplanner.dto.expenses.CreateExpenseDTO
import com.eliasjuniornino.budgetplanner.dto.expenses.UpdateExpenseDTO
import com.eliasjuniornino.budgetplanner.getAccountOrRespondError
import com.eliasjuniornino.budgetplanner.getValidIdOrRespondError
import com.eliasjuniornino.budgetplanner.models.CreateExpenseModel
import com.eliasjuniornino.budgetplanner.repositories.expenses.ExpensesRepository
import io.ktor.http.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class ExpensesControllerImpl(private val repository: ExpensesRepository) : ExpensesController {
    override suspend fun index(context: RoutingContext) = context.apply {
        val account = getAccountOrRespondError() ?: return@apply
        val expenses = repository.list(account.id)
        call.respond(expenses.map { it.toDTO() })
    }

    override suspend fun view(context: RoutingContext) = context.apply {
        val id = call.getValidIdOrRespondError() ?: return@apply
        val account = getAccountOrRespondError() ?: return@apply

        val expense = repository.get(account.id, id)
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

        val account = getAccountOrRespondError() ?: return@apply

        if (repository.existsByName(account.id, request.name)) {
            call.respond(HttpStatusCode.Conflict, mapOf("message" to "Expense with this name already exists"))
            return@apply
        }

        val now = LocalDateTime.now()
        val formatter = DateTimeFormatter.ISO_LOCAL_DATE_TIME

        val newExpense = CreateExpenseModel(
            accountId = account.id,
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

        val createdExpense = repository.store(account.id, newExpense)
        call.respond(HttpStatusCode.Created, createdExpense.toDTO())
    }

    override suspend fun update(context: RoutingContext) = context.apply {
        val request = call.receive<UpdateExpenseDTO>()

        if (!request.validate()) {
            call.respond(HttpStatusCode.BadRequest, mapOf("message" to "Invalid expense"))
            return@apply
        }

        val account = getAccountOrRespondError() ?: return@apply

        val expense = repository.get(account.id, request.id)
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

        val updatedExpense = repository.update(account.id, expense)
        call.respond(updatedExpense.toDTO())
    }

    override suspend fun delete(context: RoutingContext) = context.apply {
        val id = call.getValidIdOrRespondError() ?: return@apply
        val account = getAccountOrRespondError() ?: return@apply

        val expense = repository.get(account.id, id)
        if (expense == null) {
            call.respond(HttpStatusCode.NotFound, mapOf("message" to "Expense not found"))
            return@apply
        }

        val deleted = repository.delete(account.id, id)
        if (!deleted) {
            call.respond(HttpStatusCode.InternalServerError, mapOf("message" to "Error deleting expense"))
        } else {
            call.respond(mapOf("message" to "Expense removed"))
        }
    }
}
