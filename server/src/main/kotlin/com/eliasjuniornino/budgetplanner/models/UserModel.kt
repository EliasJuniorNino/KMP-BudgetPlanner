package com.eliasjuniornino.budgetplanner.models

import com.eliasjuniornino.budgetplanner.dto.user.UserDTO

data class UserModel(
    val id: Int,
    var name: String,
    val email: String,
    val password: String
) {
    fun toDTO() = UserDTO(id, name, email)
}