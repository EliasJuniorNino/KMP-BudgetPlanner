package com.eliasjuniornino.budgetplanner.models

import com.eliasjuniornino.budgetplanner.dto.expense_categories.ExpenseCategoryDTO
import com.eliasjuniornino.budgetplanner.utils.getCurrentLocalDateTime
import org.jetbrains.exposed.dao.IntEntity
import org.jetbrains.exposed.dao.IntEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.IntIdTable
import org.jetbrains.exposed.sql.javatime.*
import java.time.LocalDateTime

data class ExpenseCategoryModel(
    var userId: Int,
    val id: Int,
    var name: String,
    var color: String? = null,
    var icon: String? = null,
    var parentId: Int? = null,
    var createdAt: LocalDateTime = getCurrentLocalDateTime(),
    var updatedAt: LocalDateTime = getCurrentLocalDateTime(),
) {
    fun toDTO() = ExpenseCategoryDTO(
        id, name, color, icon, parentId,
        createdAt = createdAt.toString(),
        updatedAt = updatedAt.toString()
    )
}

data class CreateExpenseCategoryModel(
    var userId: Int,
    var name: String,
    var color: String? = null,
    var icon: String? = null,
    var parentId: Int? = null,
)

object ExpenseCategoryTable : IntIdTable("expense_categories") {
    val userId = reference("user_id", UserTable)
    val parentId = reference("parent_id", ExpenseCategoryTable).nullable()

    val name = varchar("name", 100)
    val color = varchar("color", 30).nullable()
    val icon = varchar("icon", 100).nullable()
    val createdAt = datetime("created_at").defaultExpression(CurrentDateTime)
    val updatedAt = datetime("updated_at").defaultExpression(CurrentDateTime)
}

class ExpenseCategoryDAO(id: EntityID<Int>) : IntEntity(id) {
    companion object : IntEntityClass<ExpenseCategoryDAO>(ExpenseCategoryTable)

    var user by UserDAO referencedOn ExpenseCategoryTable.userId
    var parent by ExpenseCategoryDAO optionalReferencedOn ExpenseCategoryTable.parentId

    var name by ExpenseCategoryTable.name
    var color by ExpenseCategoryTable.color
    var icon by ExpenseCategoryTable.icon
    var createdAt by ExpenseCategoryTable.createdAt
    var updatedAt by ExpenseCategoryTable.updatedAt
}

fun daoToModel(dao: ExpenseCategoryDAO) = ExpenseCategoryModel(
    userId = dao.user.id.value,
    parentId = dao.parent?.id?.value,

    id = dao.id.value,
    name = dao.name,
    color = dao.color,
    icon = dao.icon,
    createdAt = dao.createdAt,
    updatedAt = dao.updatedAt
)
