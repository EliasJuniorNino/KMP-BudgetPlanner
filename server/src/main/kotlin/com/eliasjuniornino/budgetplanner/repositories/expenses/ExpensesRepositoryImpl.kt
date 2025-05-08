package com.eliasjuniornino.budgetplanner.repositories.expenses

import com.eliasjuniornino.budgetplanner.models.*
import kotlinx.coroutines.Dispatchers
import org.jetbrains.exposed.sql.and
import org.jetbrains.exposed.sql.transactions.experimental.newSuspendedTransaction

class ExpensesRepositoryImpl : ExpensesRepository {

    override suspend fun list(userId: Int): List<ExpenseModel> = newSuspendedTransaction(Dispatchers.IO) {
        ExpenseDAO
            .find { ExpenseTable.userId eq userId }
            .map { daoToModel(it) }
    }

    override suspend fun store(userId: Int, data: CreateExpenseModel): ExpenseModel =
        newSuspendedTransaction(Dispatchers.IO) {
            val dao = ExpenseDAO.new {
                this.user = UserDAO[userId]
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

    override suspend fun get(userId: Int, id: Int): ExpenseModel? = newSuspendedTransaction(Dispatchers.IO) {
        ExpenseDAO
            .find { (ExpenseTable.id eq id) and (ExpenseTable.userId eq userId) }
            .firstOrNull()?.let { daoToModel(it) }
    }

    override suspend fun update(userId: Int, data: ExpenseModel): ExpenseModel =
        newSuspendedTransaction(Dispatchers.IO) {
            val dao = ExpenseDAO
                .find { (ExpenseTable.id eq data.id) and (ExpenseTable.userId eq userId) }
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

    override suspend fun delete(userId: Int, id: Int): Boolean = newSuspendedTransaction(Dispatchers.IO) {
        val dao = ExpenseDAO
            .find { (ExpenseTable.id eq id) and (ExpenseTable.userId eq userId) }
            .firstOrNull()

        dao?.delete()
        dao != null
    }

    override suspend fun existsByName(userId: Int, name: String): Boolean = newSuspendedTransaction(Dispatchers.IO) {
        ExpenseDAO
            .find { (ExpenseTable.userId eq userId) and (ExpenseTable.name eq name) }
            .empty()
            .not()
    }
}
