package com.eliasjuniornino.budgetplanner.repositories.users

import com.eliasjuniornino.budgetplanner.models.CreateUserModel
import com.eliasjuniornino.budgetplanner.models.UserModel

interface UsersRepository {
    suspend fun findByEmail(email: String): UserModel?
    suspend fun findByEmailAndPassword(email: String, password: String): UserModel?
    suspend fun existsByEmail(email: String): Boolean
    suspend fun store(newUser: CreateUserModel): UserModel
}