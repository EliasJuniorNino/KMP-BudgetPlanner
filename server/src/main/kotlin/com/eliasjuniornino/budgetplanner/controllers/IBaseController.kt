package com.eliasjuniornino.budgetplanner.controllers

import io.ktor.server.routing.*

interface IBaseController {
    suspend fun index(context: RoutingContext) : RoutingContext
    suspend fun view(context: RoutingContext) : RoutingContext
    suspend fun create(context: RoutingContext) : RoutingContext
    suspend fun update(context: RoutingContext) : RoutingContext
    suspend fun delete(context: RoutingContext) : RoutingContext
}