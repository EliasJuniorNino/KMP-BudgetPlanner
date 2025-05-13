package com.eliasjuniornino.budgetplanner.dto.accounts

import kotlinx.serialization.Serializable

@Serializable
data class UpdateAccountDTO(
    val id: Int,
    val name: String
) {
    fun validate() = true
}