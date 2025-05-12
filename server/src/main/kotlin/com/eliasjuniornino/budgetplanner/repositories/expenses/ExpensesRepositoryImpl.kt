package com.eliasjuniornino.budgetplanner.repositories.expenses

import com.eliasjuniornino.budgetplanner.dao.AccountDAO
import com.eliasjuniornino.budgetplanner.dao.ExpenseDAO
import com.eliasjuniornino.budgetplanner.dao.ExpenseTable
import com.eliasjuniornino.budgetplanner.dao.daoToModel
import com.eliasjuniornino.budgetplanner.models.CreateExpenseModel
import com.eliasjuniornino.budgetplanner.models.ExpenseModel
import kotlinx.coroutines.Dispatchers
import org.jetbrains.exposed.sql.and
import org.jetbrains.exposed.sql.transactions.experimental.newSuspendedTransaction

class ExpensesRepositoryImpl : ExpensesRepository {

    override suspend fun list(accountId: Int): List<ExpenseModel> = newSuspendedTransaction(Dispatchers.IO) {
        ExpenseDAO
            .find { ExpenseTable.accountId eq accountId }
            .map { daoToModel(it) }
    }

    override suspend fun store(accountId: Int, data: CreateExpenseModel): ExpenseModel =
        newSuspendedTransaction(Dispatchers.IO) {
            val dao = ExpenseDAO.new {
                this.account = AccountDAO[accountId]
                this.name = data.name
                this.expenseType = data.expenseType
                this.value = data.value
                this.valueMultiplier = data.valueMultiplier
                this.categoryId = data.categoryId
                this.subCategoryId = data.subCategoryId
                this.isEssential = data.isEssential
                this.description = data.description
                this.parcelCurrent = data.parcelCurrent
                this.parcelTotal = data.parcelTotal
                this.frequencyType = data.frequencyType
                this.frequencyValue = data.frequencyValue
                this.isDone = data.isDone
                this.date = data.date
                this.dateStart = data.dateStart
                this.createdAt = data.createdAt
                this.updatedAt = data.updatedAt
            }
            daoToModel(dao)
        }

    override suspend fun get(accountId: Int, id: Int): ExpenseModel? = newSuspendedTransaction(Dispatchers.IO) {
        ExpenseDAO
            .find { (ExpenseTable.id eq id) and (ExpenseTable.accountId eq accountId) }
            .firstOrNull()?.let { daoToModel(it) }
    }

    override suspend fun update(accountId: Int, data: ExpenseModel): ExpenseModel =
        newSuspendedTransaction(Dispatchers.IO) {
            val dao = ExpenseDAO
                .find { (ExpenseTable.id eq data.id) and (ExpenseTable.accountId eq accountId) }
                .firstOrNull() ?: throw NoSuchElementException("Expense not found")

            dao.apply {
                name = data.name
                expenseType = data.expenseType
                value = data.value
                valueMultiplier = data.valueMultiplier
                categoryId = data.categoryId
                subCategoryId = data.subCategoryId
                isEssential = data.isEssential
                description = data.description
                parcelCurrent = data.parcelCurrent
                parcelTotal = data.parcelTotal
                frequencyType = data.frequencyType
                frequencyValue = data.frequencyValue
                isDone = data.isDone
                date = data.date
                dateStart = data.dateStart
                createdAt = data.createdAt
                updatedAt = data.updatedAt
            }

            daoToModel(dao)
        }

    override suspend fun delete(accountId: Int, id: Int): Boolean = newSuspendedTransaction(Dispatchers.IO) {
        val dao = ExpenseDAO
            .find { (ExpenseTable.id eq id) and (ExpenseTable.accountId eq accountId) }
            .firstOrNull()

        dao?.delete()
        dao != null
    }

    override suspend fun existsByName(accountId: Int, name: String): Boolean = newSuspendedTransaction(Dispatchers.IO) {
        ExpenseDAO
            .find { (ExpenseTable.accountId eq accountId) and (ExpenseTable.name eq name) }
            .empty()
            .not()
    }
}
