package com.eliasjuniornino.budgetplanner.repositories.accounts

import com.eliasjuniornino.budgetplanner.models.AccountModel
import com.eliasjuniornino.budgetplanner.models.CreateAccountModel

interface AccountsRepository {
    suspend fun list(userId: Int): List<AccountModel>
    suspend fun store(data: CreateAccountModel): AccountModel
    suspend fun get(userId: Int, accountId: Int): AccountModel?
    suspend fun update(userId: Int, data: AccountModel): AccountModel
    suspend fun delete(userId: Int, accountId: Int): Boolean
    suspend fun existsByName(userId: Int, accountId: Int, name: String): Boolean
}