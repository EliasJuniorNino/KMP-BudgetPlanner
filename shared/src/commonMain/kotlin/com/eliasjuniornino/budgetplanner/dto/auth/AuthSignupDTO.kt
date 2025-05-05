package com.eliasjuniornino.budgetplanner.dto.auth

import kotlinx.serialization.Serializable

@Serializable
data class AuthSignupDTO(
    val name: String,
    val email: String,
    val password: String
)