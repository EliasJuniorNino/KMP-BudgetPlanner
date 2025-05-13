package com.eliasjuniornino.budgetplanner.repositories

import com.eliasjuniornino.budgetplanner.dto.accounts.AccountDTO
import com.eliasjuniornino.budgetplanner.dto.auth.AuthLoginDTO
import com.eliasjuniornino.budgetplanner.dto.auth.AuthLoginReturnDTO
import com.eliasjuniornino.budgetplanner.dto.auth.AuthSignupDTO
import com.eliasjuniornino.budgetplanner.dto.auth.AuthSignupReturnDTO
import com.eliasjuniornino.budgetplanner.dto.user.UserDTO

interface UserRepository {
    suspend fun doLogin(data: AuthLoginDTO) : AuthLoginReturnDTO
    suspend fun doSignup(data: AuthSignupDTO) : AuthSignupReturnDTO
    suspend fun getUser(): UserDTO
    suspend fun getAccounts(): List<AccountDTO>
}