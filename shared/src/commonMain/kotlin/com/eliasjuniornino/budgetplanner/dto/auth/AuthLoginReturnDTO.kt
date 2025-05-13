package com.eliasjuniornino.budgetplanner.dto.auth

import com.eliasjuniornino.budgetplanner.dto.accounts.AccountDTO
import com.eliasjuniornino.budgetplanner.dto.user.UserDTO
import kotlinx.serialization.Serializable

@Serializable
data class AuthLoginReturnDTO(
    val token: String,
    val user: UserDTO,
    val accounts: List<AccountDTO>
)