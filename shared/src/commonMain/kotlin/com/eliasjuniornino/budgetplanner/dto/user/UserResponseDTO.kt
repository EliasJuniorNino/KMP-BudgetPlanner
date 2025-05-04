package com.eliasjuniornino.budgetplanner.dto.user

import kotlinx.serialization.Serializable

@Serializable
data class UserResponseDTO(
    val name: String,
    val email: String
)