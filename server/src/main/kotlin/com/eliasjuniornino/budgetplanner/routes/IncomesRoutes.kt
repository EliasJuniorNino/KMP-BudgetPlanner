package com.eliasjuniornino.budgetplanner.routes

import com.eliasjuniornino.budgetplanner.controllers.incomes.IncomesControllerImpl
import com.eliasjuniornino.budgetplanner.repositories.incomes.IncomesRepositoryImpl
import io.ktor.server.routing.*

fun Route.getIncomesRoutes() {
    val incomesService = IncomesRepositoryImpl()
    val incomesController = IncomesControllerImpl(incomesService)

    get("/incomes", incomesController::index)
    post("/incomes", incomesController::create)
    get("/incomes/{id}", incomesController::view)
    put("/incomes", incomesController::update)
    delete("/incomes/{id}", incomesController::delete)
}