package com.eliasjuniornino.budgetplanner.repositories.users

import com.eliasjuniornino.budgetplanner.models.UserModel

interface IUsersRepository {
    suspend fun findByEmail(email: String): UserModel?
    suspend fun findByEmailAndPassword(email: String, password: String): UserModel?
}