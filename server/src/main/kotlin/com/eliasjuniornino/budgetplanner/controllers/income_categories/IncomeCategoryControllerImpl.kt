package com.eliasjuniornino.budgetplanner.controllers.income_categories

import com.eliasjuniornino.budgetplanner.dto.income_categories.CreateIncomeCategoryDTO
import com.eliasjuniornino.budgetplanner.dto.income_categories.UpdateIncomeCategoryDTO
import com.eliasjuniornino.budgetplanner.getAccountOrRespondError
import com.eliasjuniornino.budgetplanner.getValidIdOrRespondError
import com.eliasjuniornino.budgetplanner.models.CreateIncomeCategoryModel
import com.eliasjuniornino.budgetplanner.repositories.incomes_categories.IncomeCategoriesRepository
import io.ktor.http.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

class IncomeCategoryControllerImpl(
    private val incomeCategoriesRepository: IncomeCategoriesRepository
) : IncomeCategoryController {
    override suspend fun index(context: RoutingContext) = context.apply {
        val account = getAccountOrRespondError() ?: return@apply
        val categories = incomeCategoriesRepository.list(account.id)
        call.respond(categories.map { it.toDTO() })
    }

    override suspend fun indexByCategory(context: RoutingContext) = context.apply {
        val parentId = call.getValidIdOrRespondError() ?: return@apply
        val account = getAccountOrRespondError() ?: return@apply
        val categories = incomeCategoriesRepository.list(account.id, parentId)
        call.respond(categories.map { it.toDTO() })
    }

    override suspend fun view(context: RoutingContext) = context.apply {
        val categoryId = call.getValidIdOrRespondError() ?: return@apply
        val account = getAccountOrRespondError() ?: return@apply

        val category = incomeCategoriesRepository.get(account.id, categoryId)
        if (category == null) {
            call.respond(HttpStatusCode.NotFound, mapOf("message" to "Category not found"))
        } else {
            call.respond(category.toDTO())
        }
    }

    override suspend fun create(context: RoutingContext) = context.apply {
        val request = call.receive<CreateIncomeCategoryDTO>()
        if (!request.validate()) {
            call.respond(HttpStatusCode.BadRequest, mapOf("message" to "Invalid category"))
            return@apply
        }

        val account = getAccountOrRespondError() ?: return@apply

        if (request.name != null && incomeCategoriesRepository.existsByName(account.id, request.name!!)) {
            call.respond(
                HttpStatusCode.Conflict,
                mapOf("message" to "Category with this name already exists")
            )
            return@apply
        }

        val newCategory = CreateIncomeCategoryModel(
            accountId = account.id,
            name = request.name!!,
            color = request.color,
            icon = request.icon,
            parentId = request.parentId
        )

        val createdCategory = incomeCategoriesRepository.store(account.id, newCategory)
        call.respond(HttpStatusCode.Created, createdCategory.toDTO())
    }

    override suspend fun update(context: RoutingContext) = context.apply {
        val request = call.receive<UpdateIncomeCategoryDTO>()

        if (!request.validate()) {
            call.respond(HttpStatusCode.BadRequest, mapOf("message" to "Invalid category"))
            return@apply
        }

        val account = getAccountOrRespondError() ?: return@apply

        val category = incomeCategoriesRepository.get(account.id, request.id)
        if (category == null) {
            call.respond(HttpStatusCode.NotFound, mapOf("message" to "Category not found"))
            return@apply
        }

        category.apply {
            request.name?.let { name = it }
            request.color?.let { color = it }
            request.icon?.let { icon = it }
            request.parentId?.let { parentId = it }
        }

        val updatedCategory = incomeCategoriesRepository.update(account.id, category)
        call.respond(updatedCategory.toDTO())
    }

    override suspend fun delete(context: RoutingContext) = context.apply {
        val categoryId = call.getValidIdOrRespondError() ?: return@apply
        val account = getAccountOrRespondError() ?: return@apply

        val category = incomeCategoriesRepository.get(account.id, categoryId)
        if (category == null) {
            call.respond(HttpStatusCode.NotFound, mapOf("message" to "Category not found"))
            return@apply
        }

        val deleted = incomeCategoriesRepository.delete(account.id, categoryId)
        if (!deleted) {
            call.respond(HttpStatusCode.InternalServerError, mapOf("message" to "Error deleting category"))
        } else {
            call.respond(mapOf("message" to "Category removed"))
        }
    }
}
