package com.eliasjuniornino.budgetplanner.repositories.incomes

import com.eliasjuniornino.budgetplanner.models.*
import kotlinx.coroutines.Dispatchers
import org.jetbrains.exposed.sql.and
import org.jetbrains.exposed.sql.transactions.experimental.newSuspendedTransaction
import java.time.LocalDateTime

class IncomesRepositoryImpl : IncomesRepository {
    override suspend fun list(userId: Int): List<IncomeModel> = newSuspendedTransaction(Dispatchers.IO) {
        IncomeDAO
            .find { IncomeTable.userId eq userId }
            .map { daoToModel(it) }
    }

    override suspend fun store(userId: Int, data: CreateIncomeModel): IncomeModel =
        newSuspendedTransaction(Dispatchers.IO) {
            val dao = IncomeDAO.new {
                this.user = UserDAO[userId]
                this.name = data.name
                this.incomeType = data.incomeType
                this.value = data.value
                this.valueMultiplier = data.valueMultiplier
                this.categoryId = data.categoryId
                this.subCategoryId = data.subCategoryId
                this.description = data.description
                this.isDone = data.isDone
                this.date = data.date
            }
            daoToModel(dao)
        }

    override suspend fun get(userId: Int, id: Int): IncomeModel? = newSuspendedTransaction(Dispatchers.IO) {
        IncomeDAO
            .find { (IncomeTable.id eq id) and (IncomeTable.userId eq userId) }
            .firstOrNull()
            ?.let { daoToModel(it) }
    }

    override suspend fun update(userId: Int, data: IncomeModel): IncomeModel =
        newSuspendedTransaction(Dispatchers.IO) {
            val dao = IncomeDAO
                .find { (IncomeTable.id eq data.id) and (IncomeTable.userId eq userId) }
                .firstOrNull() ?: throw NoSuchElementException("Income not found")

            dao.apply {
                name = data.name
                incomeType = data.incomeType
                value = data.value
                valueMultiplier = data.valueMultiplier
                categoryId = data.categoryId
                subCategoryId = data.subCategoryId
                description = data.description
                isDone = data.isDone
                updatedAt = LocalDateTime.now()
            }

            daoToModel(dao)
        }

    override suspend fun delete(userId: Int, id: Int): Boolean = newSuspendedTransaction(Dispatchers.IO) {
        val dao = IncomeDAO
            .find { (IncomeTable.id eq id) and (IncomeTable.userId eq userId) }
            .firstOrNull()

        dao?.delete()
        dao != null
    }

    override suspend fun existsByName(userId: Int, name: String): Boolean = newSuspendedTransaction(Dispatchers.IO) {
        IncomeDAO
            .find { (IncomeTable.userId eq userId) and (IncomeTable.name eq name) }
            .empty().not()
    }
}
