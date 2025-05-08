package com.eliasjuniornino.budgetplanner.models

import com.eliasjuniornino.budgetplanner.dto.income_categories.IncomeCategoryDTO
import com.eliasjuniornino.budgetplanner.utils.getCurrentLocalDateTime
import org.jetbrains.exposed.dao.IntEntity
import org.jetbrains.exposed.dao.IntEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.IntIdTable
import org.jetbrains.exposed.sql.javatime.*
import java.time.LocalDateTime

data class IncomeCategoryModel(
    var userId: Int,
    val id: Int,
    var name: String,
    var color: String? = null,
    var icon: String? = null,
    var parentId: Int? = null,
    var createdAt: LocalDateTime = getCurrentLocalDateTime(),
    var updatedAt: LocalDateTime = getCurrentLocalDateTime(),
) {
    fun toDTO() = IncomeCategoryDTO(
        id, name, color, icon, parentId,
        createdAt = createdAt.toString(),
        updatedAt = updatedAt.toString()
    )
}

data class CreateIncomeCategoryModel(
    var userId: Int,
    var name: String,
    var color: String? = null,
    var icon: String? = null,
    var parentId: Int? = null,
)

object IncomeCategoryTable : IntIdTable("income_categories") {
    val userId = reference("user_id", UserTable)
    val parentId = reference("parent_id", IncomeCategoryTable).nullable()

    val name = varchar("name", 100)
    val color = varchar("color", 30).nullable()
    val icon = varchar("icon", 100).nullable()
    val createdAt = datetime("created_at").defaultExpression(CurrentDateTime)
    val updatedAt = datetime("updated_at").defaultExpression(CurrentDateTime)
}

class IncomeCategoryDAO(id: EntityID<Int>) : IntEntity(id) {
    companion object : IntEntityClass<IncomeCategoryDAO>(IncomeCategoryTable)

    var user by UserDAO referencedOn IncomeCategoryTable.userId
    var parent by IncomeCategoryDAO optionalReferencedOn IncomeCategoryTable.parentId

    var name by IncomeCategoryTable.name
    var color by IncomeCategoryTable.color
    var icon by IncomeCategoryTable.icon
    var createdAt by IncomeCategoryTable.createdAt
    var updatedAt by IncomeCategoryTable.updatedAt
}

fun daoToModel(dao: IncomeCategoryDAO) = IncomeCategoryModel(
    userId = dao.user.id.value,
    parentId = dao.parent?.id?.value,

    id = dao.id.value,
    name = dao.name,
    color = dao.color,
    icon = dao.icon,
    createdAt = dao.createdAt,
    updatedAt = dao.updatedAt
)
