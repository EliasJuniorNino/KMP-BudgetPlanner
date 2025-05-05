package com.eliasjuniornino.budgetplanner.models

import com.eliasjuniornino.budgetplanner.utils.LocalDateTimeSerializer
import org.jetbrains.exposed.dao.IntEntity
import org.jetbrains.exposed.dao.IntEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.IntIdTable
import org.jetbrains.exposed.sql.javatime.datetime
import java.time.LocalDateTime
import com.eliasjuniornino.budgetplanner.utils.getCurrentLocalDateTime

enum class ExpenseType {
    UNIQUE, FREQUENCY, PARCEL
}

enum class FrequencyType {
    UNIQUE, DAILY, WEEKLY, MONTHLY, YEARLY, DAYS_OF_WEEK, MONTHS_OF_YEAR, EACH_TWO_MONTHS
}

data class ExpenseModel(
    val id: Int,
    val data: CreateExpenseModel
)

data class CreateExpenseModel(
    val userId: Int,
    var name: String,
    var expenseType: ExpenseType,
    var value: Double = 0.0,
    var valueMultiplier: Double? = 1.0,
    var categoryId: Int? = null,
    var subCategoryId: Int? = null,
    var isEssential: Boolean = false,
    var description: String? = null,
    var parcelCurrent: Int? = null,
    var parcelTotal: Int? = null,
    var frequencyType: FrequencyType? = null,
    var frequencyValue: String? = null,
    var isDone: Boolean = false,
    var date: LocalDateTime = getCurrentLocalDateTime(),
    var dateStart: LocalDateTime = getCurrentLocalDateTime(),
    var createdAt: LocalDateTime = getCurrentLocalDateTime(),
    var updatedAt: LocalDateTime = getCurrentLocalDateTime(),
)

object ExpenseTable : IntIdTable("expense") {
    val user = reference("user_id", UserTable)

    val name = varchar("name", 100)
    val expenseType = enumerationByName("expense_type", 20, ExpenseType::class)
    val value = double("value").default(0.0)
    val valueMultiplier = double("value_multiplier").nullable()
    val categoryId = integer("category_id").nullable()
    val subCategoryId = integer("sub_category_id").nullable()
    val isEssential = bool("is_essential").default(false)
    val description = varchar("description", 255).nullable()
    val parcelCurrent = integer("parcel_current").nullable()
    val parcelTotal = integer("parcel_total").nullable()
    val frequencyType = enumerationByName("frequency_type", 30, FrequencyType::class).nullable()
    val frequencyValue = varchar("frequency_value", 50).nullable()
    val isDone = bool("is_done").default(false)
    val date = datetime("date")
    val dateStart = datetime("date_start")
    val createdAt = datetime("created_at")
    val updatedAt = datetime("updated_at")
}

class ExpenseDAO(id: EntityID<Int>) : IntEntity(id) {
    companion object : IntEntityClass<ExpenseDAO>(ExpenseTable)

    var user by UserDAO referencedOn ExpenseTable.user

    var name by ExpenseTable.name
    var expenseType by ExpenseTable.expenseType
    var value by ExpenseTable.value
    var valueMultiplier by ExpenseTable.valueMultiplier
    var categoryId by ExpenseTable.categoryId
    var subCategoryId by ExpenseTable.subCategoryId
    var isEssential by ExpenseTable.isEssential
    var description by ExpenseTable.description
    var parcelCurrent by ExpenseTable.parcelCurrent
    var parcelTotal by ExpenseTable.parcelTotal
    var frequencyType by ExpenseTable.frequencyType
    var frequencyValue by ExpenseTable.frequencyValue
    var isDone by ExpenseTable.isDone
    var date by ExpenseTable.date
    var dateStart by ExpenseTable.dateStart
    var createdAt by ExpenseTable.createdAt
    var updatedAt by ExpenseTable.updatedAt
}

fun daoToModel(dao: ExpenseDAO) = ExpenseModel(
    id = dao.id.value,
    data = CreateExpenseModel(
        userId = 0,
        name = dao.name,
        expenseType = dao.expenseType,
        value = dao.value,
        valueMultiplier = dao.valueMultiplier,
        categoryId = dao.categoryId,
        subCategoryId = dao.subCategoryId,
        isEssential = dao.isEssential,
        description = dao.description,
        parcelCurrent = dao.parcelCurrent,
        parcelTotal = dao.parcelTotal,
        frequencyType = dao.frequencyType,
        frequencyValue = dao.frequencyValue,
        isDone = dao.isDone,
        date = dao.date,
        dateStart = dao.dateStart,
        createdAt = dao.createdAt,
        updatedAt = dao.updatedAt,
    )
)
