package com.eliasjuniornino.budgetplanner.routes

import com.eliasjuniornino.budgetplanner.controllers.income_categories.IncomeCategoryControllerImpl
import com.eliasjuniornino.budgetplanner.repositories.incomes_categories.IncomeCategoriesRepositoryImpl
import io.ktor.server.routing.*

fun Route.getIncomeCategoryRoutes() {
    val incomeCategoryService = IncomeCategoriesRepositoryImpl()
    val incomeCategoryController = IncomeCategoryControllerImpl(incomeCategoryService)

    get("/income-categories", incomeCategoryController::index)
    post("/income-categories", incomeCategoryController::create)
    get("/income-categories/{id}", incomeCategoryController::view)
    put("/income-categories", incomeCategoryController::update)
    delete("/income-categories/{id}", incomeCategoryController::delete)
    get("/income-sub-categories/{id}", incomeCategoryController::indexByCategory)
}