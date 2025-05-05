package com.eliasjuniornino.budgetplanner

import com.eliasjuniornino.budgetplanner.models.UserModel
import io.ktor.util.*

val AUTHENTICATED_USER_KEY = AttributeKey<UserModel>("AuthenticatedUser")