package com.eliasjuniornino.budgetplanner.dao

import com.eliasjuniornino.budgetplanner.dto.expenses.ExpenseType
import com.eliasjuniornino.budgetplanner.dto.expenses.FrequencyType
import com.eliasjuniornino.budgetplanner.models.ExpenseModel
import org.jetbrains.exposed.dao.IntEntity
import org.jetbrains.exposed.dao.IntEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.IntIdTable
import org.jetbrains.exposed.sql.javatime.*

object ExpenseTable : IntIdTable("expenses") {
    val userId = reference("user_id", UserTable)
    val accountId = reference("account_id", AccountTable)

    val name = varchar("name", 100)
    val expenseType = enumerationByName("expense_type", 20, ExpenseType::class)
    val value = double("value").default(0.0)
    val valueMultiplier = double("value_multiplier").default(1.0)
    val categoryId = integer("category_id").nullable()
    val subCategoryId = integer("sub_category_id").nullable()
    val isEssential = bool("is_essential").default(false)
    val description = varchar("description", 255).nullable()
    val parcelCurrent = integer("parcel_current").nullable()
    val parcelTotal = integer("parcel_total").nullable()
    val frequencyType = enumerationByName("frequency_type", 30, FrequencyType::class).nullable()
    val frequencyValue = varchar("frequency_value", 50).nullable()
    val isDone = bool("is_done").default(false)
    val date = datetime("date").defaultExpression(CurrentDateTime)
    val dateStart = datetime("date_start").nullable()
    val createdAt = datetime("created_at").defaultExpression(CurrentDateTime)
    val updatedAt = datetime("updated_at").defaultExpression(CurrentDateTime)
}

class ExpenseDAO(id: EntityID<Int>) : IntEntity(id) {
    companion object : IntEntityClass<ExpenseDAO>(ExpenseTable)

    var user by UserDAO referencedOn ExpenseTable.userId
    var account by AccountDAO referencedOn ExpenseTable.accountId

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
    userId = dao.user.id.value,
    accountId = dao.account.id.value,
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