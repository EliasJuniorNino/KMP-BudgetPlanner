package com.eliasjuniornino.budgetplanner.routes

import com.eliasjuniornino.budgetplanner.controllers.categories.CategoriesController
import com.eliasjuniornino.budgetplanner.repositories.categories.CategoriesRepository
import io.ktor.server.routing.*

fun Route.getCategoriesRoutes() {
    val categoriesService = CategoriesRepository()
    val categoriesController = CategoriesController(categoriesService)

    get("/categories", categoriesController::index)
    post("/categories", categoriesController::create)
    get("/categories/{id}", categoriesController::view)
    put("/categories/{id}", categoriesController::update)
    delete("/categories/{id}", categoriesController::delete)
}