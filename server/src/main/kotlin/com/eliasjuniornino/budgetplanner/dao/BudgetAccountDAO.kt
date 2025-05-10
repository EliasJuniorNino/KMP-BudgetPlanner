package com.eliasjuniornino.budgetplanner.dao

import com.eliasjuniornino.budgetplanner.models.BudgetAccountModel
import org.jetbrains.exposed.dao.IntEntity
import org.jetbrains.exposed.dao.IntEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.IntIdTable
import org.jetbrains.exposed.sql.javatime.CurrentDateTime
import org.jetbrains.exposed.sql.javatime.datetime

object BudgetAccountTable : IntIdTable("budget_account") {
    val name = varchar("name", 100)
    val createdAt = datetime("created_at").defaultExpression(CurrentDateTime)
    val updatedAt = datetime("updated_at").defaultExpression(CurrentDateTime)
}

class BudgetAccountDAO(id: EntityID<Int>) : IntEntity(id) {
    companion object : IntEntityClass<BudgetAccountDAO>(BudgetAccountTable)

    var name by BudgetAccountTable.name
}

fun daoToModel(dao: BudgetAccountDAO) = BudgetAccountModel(
    id = dao.id.value,
    name = dao.name
)