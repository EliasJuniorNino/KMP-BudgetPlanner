package com.eliasjuniornino.budgetplanner.dto.accounts

import kotlinx.serialization.Serializable

@Serializable
data class CreateAccountDTO(
    val name: String
) {
    fun validate() = true
}