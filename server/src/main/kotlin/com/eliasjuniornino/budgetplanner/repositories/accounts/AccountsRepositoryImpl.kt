package com.eliasjuniornino.budgetplanner.repositories.accounts

import com.eliasjuniornino.budgetplanner.dao.AccountTable
import com.eliasjuniornino.budgetplanner.dao.AccountUserTable
import com.eliasjuniornino.budgetplanner.models.AccountModel
import com.eliasjuniornino.budgetplanner.models.CreateAccountModel
import kotlinx.coroutines.Dispatchers
import org.jetbrains.exposed.sql.IntegerColumnType
import org.jetbrains.exposed.sql.transactions.experimental.newSuspendedTransaction

class AccountsRepositoryImpl : AccountsRepository {
    override suspend fun list(userId: Int): List<AccountModel> =
        newSuspendedTransaction(Dispatchers.IO) {
            val query = """
                select id, name, user_id 
                from ${AccountTable.tableName} ba
                where ba.id in (
                    select au.id
                    from ${AccountUserTable.tableName} au
                    where au.user_id = ?
                )
                """.trimIndent()
            val args = listOf(
                IntegerColumnType() to userId
            )

            val accounts = mutableListOf<AccountModel>()

            exec(stmt = query, args = args) { rs ->
                while (rs.next()) {
                    accounts.add(
                        AccountModel(
                            id = rs.getInt("id"),
                            name = rs.getString("name"),
                            userId = rs.getInt("user_id")
                        )
                    )
                }
            }

            accounts.toList()
        }

    override suspend fun store(userId: Int, data: CreateAccountModel): AccountModel {
        TODO("Not yet implemented")
    }

    override suspend fun get(userId: Int, accountId: Int): AccountModel? {
        TODO("Not yet implemented")
    }

    override suspend fun update(userId: Int, data: AccountModel): AccountModel {
        TODO("Not yet implemented")
    }

    override suspend fun delete(userId: Int, accountId: Int): Boolean {
        TODO("Not yet implemented")
    }

    override suspend fun existsByName(userId: Int, name: String): Boolean {
        TODO("Not yet implemented")
    }
}