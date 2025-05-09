package com.eliasjuniornino.budgetplanner.repositories

import com.eliasjuniornino.budgetplanner.dto.auth.AuthLoginDTO
import com.eliasjuniornino.budgetplanner.dto.auth.AuthSignupDTO
import com.eliasjuniornino.budgetplanner.dto.user.UserDTO

interface UserRepository {
    suspend fun doLogin(data: AuthLoginDTO)
    suspend fun doSignup(data: AuthSignupDTO)
    suspend fun getUser(): UserDTO
}