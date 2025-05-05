package com.eliasjuniornino.budgetplanner.routes

import com.eliasjuniornino.budgetplanner.controllers.categories.CategoriesControllerImpl
import com.eliasjuniornino.budgetplanner.repositories.categories.CategoriesRepositoryImpl
import io.ktor.server.routing.*

fun Route.getCategoriesRoutes() {
    val categoriesService = CategoriesRepositoryImpl()
    val categoriesController = CategoriesControllerImpl(categoriesService)

    get("/categories", categoriesController::index)
    post("/categories", categoriesController::create)
    get("/categories/{id}", categoriesController::view)
    put("/categories", categoriesController::update)
    delete("/categories/{id}", categoriesController::delete)

    get("/sub-categories/{id}", categoriesController::indexByCategory)
}