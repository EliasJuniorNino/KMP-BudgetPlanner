package com.eliasjuniornino.budgetplanner.models

import kotlinx.serialization.Serializable
import org.jetbrains.exposed.dao.IntEntity
import org.jetbrains.exposed.dao.IntEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.IntIdTable

@Serializable
data class CategoryModel(
    var userId: Int,

    val id: Int? = null,
    var name: String,
    var color: String? = null,
    var icon: String? = null,
    var parentId: Long? = null,
)

object CategoryTable : IntIdTable("category") {
    val userId = reference("user_id", UserTable)

    val name = varchar("category_name", 100)
    val color = varchar("category_color", 30).nullable()
    val icon = varchar("category_icon", 100).nullable()
    val parentId = long("category_parent_id").nullable()
}

class CategoryDAO(id: EntityID<Int>) : IntEntity(id) {
    companion object : IntEntityClass<CategoryDAO>(CategoryTable)

    var user by UserDAO referencedOn CategoryTable.userId

    var name by CategoryTable.name
    var color by CategoryTable.color
    var icon by CategoryTable.icon
    var parentId by CategoryTable.parentId
}

fun daoToModel(dao: CategoryDAO) = CategoryModel(
    userId = 0,

    id = dao.id.value,
    name = dao.name,
    color = dao.color,
    icon = dao.icon
)
