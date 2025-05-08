package com.eliasjuniornino.budgetplanner.controllers.expense_categories

import com.eliasjuniornino.budgetplanner.controllers.BaseController
import io.ktor.server.routing.*

interface ExpenseCategoryController : BaseController {
    suspend fun indexByCategory(context: RoutingContext): RoutingContext
}