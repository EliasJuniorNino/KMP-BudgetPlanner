package com.eliasjuniornino.budgetplanner.controllers.income_categories

import com.eliasjuniornino.budgetplanner.controllers.BaseController
import io.ktor.server.routing.*

interface IncomeCategoryController : BaseController {
    suspend fun indexByCategory(context: RoutingContext): RoutingContext
}