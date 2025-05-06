package com.eliasjuniornino.budgetplanner.models

import com.eliasjuniornino.budgetplanner.dto.incomes.IncomeType
import com.eliasjuniornino.budgetplanner.dto.incomes.IncomesDTO
import com.eliasjuniornino.budgetplanner.utils.getCurrentLocalDateTime
import org.jetbrains.exposed.dao.IntEntity
import org.jetbrains.exposed.dao.IntEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.IntIdTable
import org.jetbrains.exposed.sql.javatime.*
import java.time.LocalDateTime

data class IncomeModel(
    val id: Int = 0,
    val userId: Int,
    var name: String,
    var incomeType: IncomeType = IncomeType.SIMPLE,
    var value: Double = 0.0,
    var valueMultiplier: Double = 1.0,
    var categoryId: Int? = null,
    var subCategoryId: Int? = null,
    var description: String? = null,
    var isDone: Boolean = false,
    var date: LocalDateTime,
    val createdAt: LocalDateTime,
    var updatedAt: LocalDateTime,
) {
    fun toDTO() = IncomesDTO(
        id = id,
        name = name,
        incomeType = incomeType,
        value = value,
        valueMultiplier = valueMultiplier,
        categoryId = categoryId,
        subCategoryId = subCategoryId,
        description = description,
        isDone = isDone,
        date = date.toString(),
        createdAt = createdAt.toString(),
        updatedAt = updatedAt.toString()
    )
}

data class CreateIncomeModel(
    var name: String,
    var incomeType: IncomeType = IncomeType.SIMPLE,
    var value: Double = 0.0,
    var valueMultiplier: Double = 1.0,
    var categoryId: Int? = null,
    var subCategoryId: Int? = null,
    var description: String? = null,
    var isDone: Boolean = false,
    var date: LocalDateTime = getCurrentLocalDateTime()
)

object IncomeTable : IntIdTable("incomes") {
    val userId = reference("user_id", UserTable)

    val name = varchar("name", 100)
    val incomeType = enumerationByName("income_type", 20, IncomeType::class)
    val value = double("value").default(.0)
    val valueMultiplier = double("value_multiplier").default(1.0)
    val categoryId = integer("category_id").nullable()
    val subCategoryId = integer("sub_category_id").nullable()
    val description = varchar("description", 255).nullable()
    val isDone = bool("is_done").default(false)
    val date = datetime("date").defaultExpression(CurrentDateTime)
    val createdAt = datetime("created_at").defaultExpression(CurrentDateTime)
    val updatedAt = datetime("updated_at").defaultExpression(CurrentDateTime)
}

class IncomeDAO(id: EntityID<Int>) : IntEntity(id) {
    companion object : IntEntityClass<IncomeDAO>(IncomeTable)

    var user by UserDAO referencedOn IncomeTable.userId

    var name by IncomeTable.name
    var incomeType by IncomeTable.incomeType
    var value by IncomeTable.value
    var valueMultiplier by IncomeTable.valueMultiplier
    var categoryId by IncomeTable.categoryId
    var subCategoryId by IncomeTable.subCategoryId
    var description by IncomeTable.description
    var isDone by IncomeTable.isDone
    var date by IncomeTable.date
    var createdAt by IncomeTable.createdAt
    var updatedAt by IncomeTable.updatedAt
}

fun daoToModel(dao: IncomeDAO) = IncomeModel(
    id = dao.id.value,
    userId = dao.user.id.value,
    name = dao.name,
    incomeType = dao.incomeType,
    value = dao.value,
    valueMultiplier = dao.valueMultiplier,
    categoryId = dao.categoryId,
    subCategoryId = dao.subCategoryId,
    description = dao.description,
    isDone = dao.isDone,
    date = dao.date,
    createdAt = dao.createdAt,
    updatedAt = dao.updatedAt
)
