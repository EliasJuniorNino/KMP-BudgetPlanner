package com.eliasjuniornino.budgetplanner.dto.auth

import kotlinx.serialization.Serializable

@Serializable
data class AuthLoginReturnDTO(
    val token: String
)