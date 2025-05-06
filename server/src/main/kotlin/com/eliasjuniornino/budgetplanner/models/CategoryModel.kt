package com.eliasjuniornino.budgetplanner.models

import com.eliasjuniornino.budgetplanner.dto.categories.CategoryDTO
import com.eliasjuniornino.budgetplanner.utils.getCurrentLocalDateTime
import org.jetbrains.exposed.dao.IntEntity
import org.jetbrains.exposed.dao.IntEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.IntIdTable
import org.jetbrains.exposed.sql.javatime.*
import java.time.LocalDateTime

data class CategoryModel(
    var userId: Int,
    val id: Int,
    var name: String,
    var color: String? = null,
    var icon: String? = null,
    var parentId: Int? = null,
    var createdAt: LocalDateTime = getCurrentLocalDateTime(),
    var updatedAt: LocalDateTime = getCurrentLocalDateTime(),
) {
    fun toDTO() = CategoryDTO(
        id, name, color, icon, parentId,
        createdAt = createdAt.toString(),
        updatedAt = updatedAt.toString()
    )
}

data class CreateCategoryModel(
    var userId: Int,
    var name: String,
    var color: String? = null,
    var icon: String? = null,
    var parentId: Int? = null,
)

object CategoryTable : IntIdTable("categories") {
    val userId = reference("user_id", UserTable)
    val parentId = reference("parent_id", CategoryTable).nullable()

    val name = varchar("name", 100)
    val color = varchar("color", 30).nullable()
    val icon = varchar("icon", 100).nullable()
    val createdAt = datetime("created_at").defaultExpression(CurrentDateTime)
    val updatedAt = datetime("updated_at").defaultExpression(CurrentDateTime)
}

class CategoryDAO(id: EntityID<Int>) : IntEntity(id) {
    companion object : IntEntityClass<CategoryDAO>(CategoryTable)

    var user by UserDAO referencedOn CategoryTable.userId
    var parent by CategoryDAO optionalReferencedOn CategoryTable.parentId

    var name by CategoryTable.name
    var color by CategoryTable.color
    var icon by CategoryTable.icon
    var createdAt by CategoryTable.createdAt
    var updatedAt by CategoryTable.updatedAt
}

fun daoToModel(dao: CategoryDAO) = CategoryModel(
    userId = dao.user.id.value,
    parentId = dao.parent?.id?.value,

    id = dao.id.value,
    name = dao.name,
    color = dao.color,
    icon = dao.icon,
    createdAt = dao.createdAt,
    updatedAt = dao.updatedAt
)
