package com.eliasjuniornino.budgetplanner.controllers.categories

import com.eliasjuniornino.budgetplanner.controllers.BaseController
import io.ktor.server.routing.*

interface CategoriesController : BaseController {
    suspend fun indexByCategory(context: RoutingContext): RoutingContext
}