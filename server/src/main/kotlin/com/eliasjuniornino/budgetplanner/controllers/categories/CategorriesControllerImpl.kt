package com.eliasjuniornino.budgetplanner.controllers.categories

import com.eliasjuniornino.budgetplanner.dto.categories.CreateCategoryDTO
import com.eliasjuniornino.budgetplanner.dto.categories.UpdateCategoryDTO
import com.eliasjuniornino.budgetplanner.getAuthenticatedUserOrRespondError
import com.eliasjuniornino.budgetplanner.models.CreateCategoryModel
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

class CategoriesControllerImpl(private val categoriesRepository: CategoriesRepository) : CategoriesController {
    override suspend fun index(context: RoutingContext) = context.apply {
        val user = getAuthenticatedUserOrRespondError() ?: return@apply
        val categories = categoriesRepository.list(user.id)
        call.respond(categories.map { it.toDTO() })
    }

    override suspend fun indexByCategory(context: RoutingContext) = context.apply {
        val parentId = call.getValidCategoryId() ?: return@apply
        val user = getAuthenticatedUserOrRespondError() ?: return@apply
        val categories = categoriesRepository.list(user.id, parentId)
        call.respond(categories.map { it.toDTO() })
    }

    override suspend fun view(context: RoutingContext) = context.apply {
        val categoryId = call.getValidCategoryId() ?: return@apply
        val user = getAuthenticatedUserOrRespondError() ?: return@apply

        val category = categoriesRepository.get(user.id, categoryId)
        if (category == null) {
            call.respond(HttpStatusCode.NotFound, mapOf("message" to "Category not found"))
        } else {
            call.respond(category.toDTO())
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

        val newCategory = CreateCategoryModel(
            userId = user.id,
            name = request.name!!,
            color = request.color,
            icon = request.icon,
            parentId = request.parentId
        )

        val createdCategory = categoriesRepository.store(user.id, newCategory)
        call.respond(HttpStatusCode.Created, createdCategory.toDTO())
    }

    override suspend fun update(context: RoutingContext) = context.apply {
        val categoryId = call.getValidCategoryId() ?: return@apply
        val request = call.receive<UpdateCategoryDTO>()

        if (!request.validate()) {
            call.respond(HttpStatusCode.BadRequest, mapOf("message" to "Invalid category"))
            return@apply
        }

        val user = getAuthenticatedUserOrRespondError() ?: return@apply

        val category = categoriesRepository.get(user.id, categoryId)
        if (category == null) {
            call.respond(HttpStatusCode.NotFound, mapOf("message" to "Category not found"))
            return@apply
        }

        category.apply {
            name = request.name!!
            color = request.color
            icon = request.icon
            parentId = request.parentId
        }

        val updatedCategory = categoriesRepository.update(user.id, category)
        call.respond(updatedCategory.toDTO())
    }

    override suspend fun delete(context: RoutingContext) = context.apply {
        val categoryId = call.getValidCategoryId() ?: return@apply
        val user = getAuthenticatedUserOrRespondError() ?: return@apply

        val category = categoriesRepository.get(user.id, categoryId)
        if (category == null) {
            call.respond(HttpStatusCode.NotFound, mapOf("message" to "Category not found"))
            return@apply
        }

        val deleted = categoriesRepository.delete(user.id, categoryId)
        if (!deleted) {
            call.respond(HttpStatusCode.InternalServerError, mapOf("message" to "Error deleting category"))
        } else {
            call.respond(mapOf("message" to "Category removed"))
        }
    }
}
