package com.eliasjuniornino.budgetplanner.routes

import com.eliasjuniornino.budgetplanner.controllers.expenses.ExpensesControllerImpl
import com.eliasjuniornino.budgetplanner.repositories.expenses.ExpensesRepositoryImpl
import io.ktor.server.routing.*

fun Route.getExpensesRoutes() {
    val expensesService = ExpensesRepositoryImpl()
    val expensesController = ExpensesControllerImpl(expensesService)

    get("/expenses", expensesController::index)
    post("/expenses", expensesController::create)
    get("/expenses/{id}", expensesController::view)
    put("/expenses", expensesController::update)
    delete("/expenses/{id}", expensesController::delete)
}