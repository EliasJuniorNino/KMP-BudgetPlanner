package com.eliasjuniornino.budgetplanner.models

import com.eliasjuniornino.budgetplanner.dto.user.UserDTO
import org.jetbrains.exposed.dao.IntEntity
import org.jetbrains.exposed.dao.IntEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.IntIdTable
import org.jetbrains.exposed.sql.javatime.*

data class UserModel(
    val id: Int,
    var name: String,
    val email: String,
    val password: String
) {
    fun toDTO() = UserDTO(id, name, email)
}

data class CreateUserModel(
    var name: String,
    val email: String,
    var password: String
)

object UserTable : IntIdTable("users") {
    val name = varchar("name", 100)
    val email = varchar("email", 100).uniqueIndex()
    val password = varchar("password", 255)
    val createdAt = datetime("created_at").defaultExpression(CurrentDateTime)
    val updatedAt = datetime("updated_at").defaultExpression(CurrentDateTime)
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
