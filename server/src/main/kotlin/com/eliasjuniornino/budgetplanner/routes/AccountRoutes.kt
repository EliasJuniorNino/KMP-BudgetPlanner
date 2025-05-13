package com.eliasjuniornino.budgetplanner.routes

import com.eliasjuniornino.budgetplanner.controllers.accounts.AccountControllerImpl
import com.eliasjuniornino.budgetplanner.repositories.accounts.AccountsRepositoryImpl
import io.ktor.server.routing.*

fun Route.getAccountRoutes() {
    val accountRepository = AccountsRepositoryImpl()
    val accountController = AccountControllerImpl(accountRepository)

    get("/accounts", accountController::index)
    post("/accounts", accountController::create)
    get("/accounts/{id}", accountController::view)
    put("/accounts", accountController::update)
    delete("/accounts/{id}", accountController::delete)
}
