package com.eliasjuniornino.budgetplanner.models

import kotlinx.serialization.Serializable
import org.jetbrains.exposed.dao.IntEntity
import org.jetbrains.exposed.dao.IntEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.IntIdTable

@Serializable
enum class IncomeType {
    Simple
}

@Serializable
enum class IncomeFrequencyType {
    UNIQUE,
    DAILY,
    WEEKLY,
    MONTHLY,
    YEARLY,
    DAYS_OF_WEEK,
    MONTHS_OF_YEAR
}

@Serializable
data class IncomeModel(
    val userId: Int,

    val id: Int = 0,
    var name: String,
    var incomeType: IncomeType,
    var value: Double? = 0.0,
    var valueMultiplier: Double? = 1.0,
    var icon: String? = null,
    var categoryId: Long? = null,
    var subCategoryId: Long? = null,
    var description: String? = null,
    var frequencyType: IncomeFrequencyType? = IncomeFrequencyType.UNIQUE,
    var frequencyValue: String? = null,
    var isDone: Boolean = false,
)

object IncomeTable : IntIdTable("income") {
    val user = reference("user_id", UserTable)

    val name = varchar("income_name", 100)
    val incomeType = enumerationByName("income_type", 20, IncomeType::class)
    val value = double("income_value").nullable()
    val valueMultiplier = double("income_value_multiplier").nullable()
    val icon = varchar("income_icon", 255).nullable()
    val categoryId = long("income_category_id").nullable()
    val subCategoryId = long("income_sub_category_id").nullable()
    val description = varchar("income_description", 255).nullable()
    val frequencyType = enumerationByName("income_frequency_type", 30, IncomeFrequencyType::class).nullable()
    val frequencyValue = varchar("income_frequency_value", 50).nullable()
    val isDone = bool("income_is_done").default(false)
}

class IncomeDAO(id: EntityID<Int>) : IntEntity(id) {
    companion object : IntEntityClass<IncomeDAO>(IncomeTable)

    var user by UserDAO referencedOn IncomeTable.user

    var name by IncomeTable.name
    var incomeType by IncomeTable.incomeType
    var value by IncomeTable.value
    var valueMultiplier by IncomeTable.valueMultiplier
    var icon by IncomeTable.icon
    var categoryId by IncomeTable.categoryId
    var subCategoryId by IncomeTable.subCategoryId
    var description by IncomeTable.description
    var frequencyType by IncomeTable.frequencyType
    var frequencyValue by IncomeTable.frequencyValue
    var isDone by IncomeTable.isDone
}

fun daoToModel(dao: IncomeDAO) = IncomeModel(
    userId = 0,

    id = dao.id.value,
    name = dao.name,
    incomeType = dao.incomeType,
    value = dao.value,
    valueMultiplier = dao.valueMultiplier,
    icon = dao.icon,
    categoryId = dao.categoryId,
    subCategoryId = dao.subCategoryId,
    description = dao.description,
    frequencyType = dao.frequencyType,
    frequencyValue = dao.frequencyValue,
    isDone = dao.isDone,
)
