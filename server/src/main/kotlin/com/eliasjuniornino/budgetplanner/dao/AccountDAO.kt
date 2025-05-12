package com.eliasjuniornino.budgetplanner.dao

import com.eliasjuniornino.budgetplanner.models.AccountModel
import org.jetbrains.exposed.dao.IntEntity
import org.jetbrains.exposed.dao.IntEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.IntIdTable
import org.jetbrains.exposed.sql.javatime.CurrentDateTime
import org.jetbrains.exposed.sql.javatime.datetime

object AccountTable : IntIdTable("budget_account") {
    val name = varchar("name", 100)
    val userId = reference("user_id", UserTable)
    val createdAt = datetime("created_at").defaultExpression(CurrentDateTime)
    val updatedAt = datetime("updated_at").defaultExpression(CurrentDateTime)
}

class AccountDAO(id: EntityID<Int>) : IntEntity(id) {
    companion object : IntEntityClass<AccountDAO>(AccountTable)

    var name by AccountTable.name
    var user by UserDAO referencedOn AccountTable.userId
}

fun daoToModel(dao: AccountDAO) = AccountModel(
    id = dao.id.value,
    name = dao.name,
    userId = dao.user.id.value,
)