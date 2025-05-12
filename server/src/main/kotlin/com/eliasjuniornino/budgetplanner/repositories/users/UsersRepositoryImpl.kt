package com.eliasjuniornino.budgetplanner.repositories.users

import com.eliasjuniornino.budgetplanner.models.CreateUserModel
import com.eliasjuniornino.budgetplanner.models.UserModel
import com.eliasjuniornino.budgetplanner.dao.UserDAO
import com.eliasjuniornino.budgetplanner.dao.UserTable
import com.eliasjuniornino.budgetplanner.dao.daoToModel
import kotlinx.coroutines.Dispatchers
import org.jetbrains.exposed.sql.and
import org.jetbrains.exposed.sql.transactions.experimental.newSuspendedTransaction

class UsersRepositoryImpl : UsersRepository {
    override suspend fun findByEmail(email: String): UserModel? = newSuspendedTransaction(Dispatchers.IO) {
        UserDAO.find { UserTable.email eq email }
            .firstOrNull()
            ?.let { daoToModel(it) }
    }

    override suspend fun findByEmailAndPassword(email: String, password: String): UserModel? =
        newSuspendedTransaction(Dispatchers.IO) {
            UserDAO
                .find { (UserTable.email eq email) and (UserTable.password eq password) }
                .firstOrNull()?.let { daoToModel(it) }
        }

    override suspend fun existsByEmail(email: String): Boolean = newSuspendedTransaction(Dispatchers.IO) {
        UserDAO.find { UserTable.email eq email }.empty().not()
    }

    override suspend fun store(newUser: CreateUserModel): UserModel = newSuspendedTransaction(Dispatchers.IO) {
        val created = UserDAO.new {
            name = newUser.name
            email = newUser.email
            password = newUser.password
        }
        daoToModel(created)
    }
}
