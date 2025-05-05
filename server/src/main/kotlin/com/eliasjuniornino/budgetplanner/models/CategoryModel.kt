package com.eliasjuniornino.budgetplanner.models

import com.eliasjuniornino.budgetplanner.dto.categories.CategoryDTO
import org.jetbrains.exposed.dao.IntEntity
import org.jetbrains.exposed.dao.IntEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.IntIdTable

data class CategoryModel(
    var userId: Int,
    val id: Int,
    var name: String,
    var color: String? = null,
    var icon: String? = null,
    var parentId: Int? = null,
) {
    fun toDTO() = CategoryDTO(
        id, name, color, icon, parentId
    )
}

data class CreateCategoryModel(
    var userId: Int,
    var name: String,
    var color: String? = null,
    var icon: String? = null,
    var parentId: Int? = null,
)

object CategoryTable : IntIdTable("category") {
    val userId = reference("user_id", UserTable)
    val parentId = reference("category_parent_id", CategoryTable).nullable()
    
    val name = varchar("category_name", 100)
    val color = varchar("category_color", 30).nullable()
    val icon = varchar("category_icon", 100).nullable()
}

class CategoryDAO(id: EntityID<Int>) : IntEntity(id) {
    companion object : IntEntityClass<CategoryDAO>(CategoryTable)

    var user by UserDAO referencedOn CategoryTable.userId
    var parent by CategoryDAO optionalReferencedOn CategoryTable.parentId

    var name by CategoryTable.name
    var color by CategoryTable.color
    var icon by CategoryTable.icon
}

fun daoToModel(dao: CategoryDAO) = CategoryModel(
    userId = dao.user.id.value,
    parentId = dao.parent?.id?.value,

    id = dao.id.value,
    name = dao.name,
    color = dao.color,
    icon = dao.icon
)
