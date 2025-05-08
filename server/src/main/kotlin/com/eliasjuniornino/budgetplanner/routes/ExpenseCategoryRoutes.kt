package com.eliasjuniornino.budgetplanner.routes

import com.eliasjuniornino.budgetplanner.controllers.expense_categories.ExpenseCategoryControllerImpl
import com.eliasjuniornino.budgetplanner.repositories.expenses_categories.ExpenseCategoriesRepositoryImpl
import io.ktor.server.routing.*

fun Route.getExpenseCategoryRoutes() {
    val incomeCategoryService = ExpenseCategoriesRepositoryImpl()
    val incomeCategoryController = ExpenseCategoryControllerImpl(incomeCategoryService)

    get("/expense-categories", incomeCategoryController::index)
    post("/expense-categories", incomeCategoryController::create)
    get("/expense-categories/{id}", incomeCategoryController::view)
    put("/expense-categories", incomeCategoryController::update)
    delete("/expense-categories/{id}", incomeCategoryController::delete)
    get("/expense-sub-categories/{id}", incomeCategoryController::indexByCategory)
}