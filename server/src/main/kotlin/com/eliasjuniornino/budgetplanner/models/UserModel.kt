package com.eliasjuniornino.budgetplanner.models

import kotlinx.serialization.Serializable
import org.jetbrains.exposed.dao.IntEntity
import org.jetbrains.exposed.dao.IntEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.IntIdTable

@Serializable
data class UserModel(
    val id: Int,
    val name: String,
    val email: String,
    val password: String
)

object UserTable : IntIdTable("user") {
    val name = varchar("name", 100)
    val email = varchar("email", 100).uniqueIndex()
    val password = varchar("password", 255)
}

class UserDAO(id: EntityID<Int>) : IntEntity(id) {
    companion object : IntEntityClass<UserDAO>(UserTable)

    var name by UserTable.name
    var email by UserTable.email
    var password by UserTable.password
}

fun daoToModel(dao: UserDAO) = UserModel(
    id = dao.id.value,
    name = dao.name,
    email = dao.email,
    password = dao.password
)
