package com.eliasjuniornino.budgetplanner.controllers.categories

import com.eliasjuniornino.budgetplanner.controllers.IBaseController
import com.eliasjuniornino.budgetplanner.dto.categories.CreateCategoryDTO
import com.eliasjuniornino.budgetplanner.dto.categories.UpdateCategoryDTO
import com.eliasjuniornino.budgetplanner.getAuthenticatedUserOrRespondError
import com.eliasjuniornino.budgetplanner.models.CategoryModel
import com.eliasjuniornino.budgetplanner.repositories.categories.CategoriesRepository
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

private suspend fun ApplicationCall.getValidCategoryId(): Int? {
    return parameters["id"]?.toIntOrNull() ?: run {
        respond(HttpStatusCode.BadRequest, mapOf("message" to "Invalid or missing category ID"))
        null
    }
}

class CategoriesController(private val categoriesRepository: CategoriesRepository) : IBaseController {

    override suspend fun index(context: RoutingContext) = context.apply {
        val user = getAuthenticatedUserOrRespondError() ?: return@apply
        call.respond(categoriesRepository.list(user.id))
    }

    override suspend fun view(context: RoutingContext) = context.apply {
        val id = call.getValidCategoryId() ?: return@apply
        val user = getAuthenticatedUserOrRespondError() ?: return@apply

        val category = categoriesRepository.get(user.id, id)
        if (category == null) {
            call.respond(HttpStatusCode.NotFound, mapOf("message" to "Category not found"))
        } else {
            call.respond(category)
        }
    }

    override suspend fun create(context: RoutingContext) = context.apply {
        val request = call.receive<CreateCategoryDTO>()
        if (!request.validate()) {
            call.respond(HttpStatusCode.BadRequest, mapOf("message" to "Invalid category"))
            return@apply
        }

        val user = getAuthenticatedUserOrRespondError() ?: return@apply

        if (request.name != null && categoriesRepository.existsByName(user.id, request.name!!)) {
            call.respond(HttpStatusCode.Conflict, mapOf("message" to "Category with this name already exists"))
            return@apply
        }

        val newCategory = CategoryModel(
            userId = user.id,
            name = request.name!!,
            color = request.color!!,
            icon = request.icon!!,
            parentId = request.parentId
        )

        val createdCategory = categoriesRepository.store(user.id, newCategory)
        call.respond(HttpStatusCode.Created, createdCategory)
    }

    override suspend fun update(context: RoutingContext) = context.apply {
        val id = call.getValidCategoryId() ?: return@apply
        val request = call.receive<UpdateCategoryDTO>()

        if (!request.validate()) {
            call.respond(HttpStatusCode.BadRequest, mapOf("message" to "Invalid category"))
            return@apply
        }

        val user = getAuthenticatedUserOrRespondError() ?: return@apply

        val category = categoriesRepository.get(user.id, id)
        if (category == null) {
            call.respond(HttpStatusCode.NotFound, mapOf("message" to "Category not found"))
            return@apply
        }

        category.apply {
            name = request.name!!
            color = request.color!!
            icon = request.icon!!
        }

        val updatedCategory = categoriesRepository.update(user.id, category)
        call.respond(updatedCategory)
    }

    override suspend fun delete(context: RoutingContext) = context.apply {
        val id = call.getValidCategoryId() ?: return@apply
        val user = getAuthenticatedUserOrRespondError() ?: return@apply

        val category = categoriesRepository.get(user.id, id)
        if (category == null) {
            call.respond(HttpStatusCode.NotFound, mapOf("message" to "Category not found"))
            return@apply
        }

        val deleted = categoriesRepository.delete(user.id, id)
        if (!deleted) {
            call.respond(HttpStatusCode.InternalServerError, mapOf("message" to "Error deleting category"))
        } else {
            call.respond(mapOf("message" to "Category removed"))
        }
    }
}
