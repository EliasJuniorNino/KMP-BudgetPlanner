package com.eliasjuniornino.budgetplanner.repositories.incomes

import com.eliasjuniornino.budgetplanner.models.CreateIncomeModel
import com.eliasjuniornino.budgetplanner.models.IncomeModel
import com.eliasjuniornino.budgetplanner.dao.AccountDAO
import com.eliasjuniornino.budgetplanner.dao.IncomeDAO
import com.eliasjuniornino.budgetplanner.dao.IncomeTable
import com.eliasjuniornino.budgetplanner.dao.daoToModel
import kotlinx.coroutines.Dispatchers
import org.jetbrains.exposed.sql.and
import org.jetbrains.exposed.sql.transactions.experimental.newSuspendedTransaction
import java.time.LocalDateTime

class IncomesRepositoryImpl : IncomesRepository {
    override suspend fun list(accountId: Int): List<IncomeModel> =
        newSuspendedTransaction(Dispatchers.IO) {
            IncomeDAO
                .find { IncomeTable.accountId eq accountId }
                .map { daoToModel(it) }
        }

    override suspend fun store(accountId: Int, data: CreateIncomeModel): IncomeModel =
        newSuspendedTransaction(Dispatchers.IO) {
            val dao = IncomeDAO.new {
                this.account = AccountDAO[accountId]
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

    override suspend fun get(accountId: Int, id: Int): IncomeModel? =
        newSuspendedTransaction(Dispatchers.IO) {
            IncomeDAO
                .find { (IncomeTable.id eq id) and (IncomeTable.accountId eq accountId) }
                .firstOrNull()?.let { daoToModel(it) }
        }

    override suspend fun update(accountId: Int, data: IncomeModel): IncomeModel =
        newSuspendedTransaction(Dispatchers.IO) {
            val dao = IncomeDAO
                .find { (IncomeTable.id eq data.id) and (IncomeTable.accountId eq accountId) }
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

    override suspend fun delete(accountId: Int, id: Int): Boolean =
        newSuspendedTransaction(Dispatchers.IO) {
            val dao = IncomeDAO
                .find { (IncomeTable.id eq id) and (IncomeTable.accountId eq accountId) }
                .firstOrNull()

            dao?.delete()
            dao != null
        }

    override suspend fun existsByName(accountId: Int, name: String): Boolean =
        newSuspendedTransaction(Dispatchers.IO) {
            IncomeDAO
                .find { (IncomeTable.accountId eq accountId) and (IncomeTable.name eq name) }
                .empty().not()
        }
}
