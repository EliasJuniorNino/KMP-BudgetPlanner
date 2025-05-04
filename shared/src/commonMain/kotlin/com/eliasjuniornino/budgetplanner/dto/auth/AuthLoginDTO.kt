package com.eliasjuniornino.budgetplanner.dto.auth

import kotlinx.serialization.Serializable

@Serializable
data class AuthLoginDTO(
    val email: String,
    val password: String
)