package com.eliasjuniornino.budgetplanner.dto.user

import kotlinx.serialization.Serializable

@Serializable
data class PublicUserDTO(
    val id: Int,
    val name: String,
    val email: String
)