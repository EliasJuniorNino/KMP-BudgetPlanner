package com.eliasjuniornino.budgetplanner.dao

import com.eliasjuniornino.budgetplanner.models.AccountUserModel
import org.jetbrains.exposed.dao.IntEntity
import org.jetbrains.exposed.dao.IntEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.IntIdTable
import org.jetbrains.exposed.sql.javatime.CurrentDateTime
import org.jetbrains.exposed.sql.javatime.datetime

object AccountUserTable : IntIdTable("account_user") {
    val userId = reference("user_id", UserTable)
    val accountId = reference("account_id", AccountTable)
    val createdAt = datetime("created_at").defaultExpression(CurrentDateTime)
    val updatedAt = datetime("updated_at").defaultExpression(CurrentDateTime)
}

class AccountUserDAO(id: EntityID<Int>) : IntEntity(id) {
    companion object : IntEntityClass<AccountUserDAO>(AccountUserTable)

    var user by UserDAO referencedOn AccountUserTable.userId
    var account by AccountDAO referencedOn AccountUserTable.accountId
}

fun daoToModel(dao: AccountUserDAO) = AccountUserModel(
    id = dao.id.value,
    userId = dao.user.id.value,
    accountId = dao.account.id.value,
)