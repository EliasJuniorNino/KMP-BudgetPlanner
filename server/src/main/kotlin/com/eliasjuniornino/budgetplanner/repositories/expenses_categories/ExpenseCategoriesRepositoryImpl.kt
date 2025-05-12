package com.eliasjuniornino.budgetplanner.repositories.expenses_categories

import com.eliasjuniornino.budgetplanner.models.ExpenseCategoryModel
import com.eliasjuniornino.budgetplanner.models.CreateExpenseCategoryModel
import com.eliasjuniornino.budgetplanner.dao.ExpenseCategoryDAO
import com.eliasjuniornino.budgetplanner.dao.ExpenseCategoryTable
import com.eliasjuniornino.budgetplanner.dao.AccountDAO
import com.eliasjuniornino.budgetplanner.dao.daoToModel
import kotlinx.coroutines.Dispatchers
import org.jetbrains.exposed.sql.and
import org.jetbrains.exposed.sql.transactions.experimental.newSuspendedTransaction

class ExpenseCategoriesRepositoryImpl : ExpenseCategoriesRepository {
    override suspend fun list(accountId: Int): List<ExpenseCategoryModel> =
        newSuspendedTransaction(Dispatchers.IO) {
            ExpenseCategoryDAO
                .find { (ExpenseCategoryTable.accountId eq accountId) and (ExpenseCategoryTable.parentId eq null) }
                .map { daoToModel(it) }
        }

    override suspend fun list(accountId: Int, parentCategoryId: Int): List<ExpenseCategoryModel> =
        newSuspendedTransaction(Dispatchers.IO) {
            ExpenseCategoryDAO
                .find { (ExpenseCategoryTable.accountId eq accountId) and (ExpenseCategoryTable.parentId eq parentCategoryId) }
                .map { daoToModel(it) }
        }

    override suspend fun store(
        accountId: Int,
        data: CreateExpenseCategoryModel
    ): ExpenseCategoryModel =
        newSuspendedTransaction(Dispatchers.IO) {
            val category = ExpenseCategoryDAO.new {
                account = AccountDAO[accountId]
                name = data.name
                color = data.color
                icon = data.icon
                parent = data.parentId?.let { ExpenseCategoryDAO[it] }
            }
            daoToModel(category)
        }

    override suspend fun get(accountId: Int, categoryId: Int): ExpenseCategoryModel? =
        newSuspendedTransaction(Dispatchers.IO) {
            ExpenseCategoryDAO
                .find {
                    (ExpenseCategoryTable.accountId eq accountId) and
                            (ExpenseCategoryTable.id eq categoryId)
                }
                .firstOrNull()?.let { daoToModel(it) }
        }

    override suspend fun update(accountId: Int, data: ExpenseCategoryModel): ExpenseCategoryModel =
        newSuspendedTransaction(Dispatchers.IO) {
            val category = ExpenseCategoryDAO
                .find { (ExpenseCategoryTable.accountId eq accountId) and (ExpenseCategoryTable.id eq data.id) }
                .firstOrNull() ?: throw NoSuchElementException("Category not found")

            category.name = data.name
            category.color = data.color
            category.icon = data.icon
            category.parent = data.parentId?.let { ExpenseCategoryDAO[it] }

            daoToModel(category)
        }

    override suspend fun delete(accountId: Int, categoryId: Int): Boolean =
        newSuspendedTransaction(Dispatchers.IO) {
            val category = ExpenseCategoryDAO
                .find { (ExpenseCategoryTable.accountId eq accountId) and (ExpenseCategoryTable.id eq categoryId) }
                .firstOrNull()

            if (category != null) {
                category.delete()
                true
            } else {
                false
            }
        }

    override suspend fun existsByName(accountId: Int, name: String): Boolean =
        newSuspendedTransaction(Dispatchers.IO) {
            ExpenseCategoryDAO
                .find { (ExpenseCategoryTable.accountId eq accountId) and (ExpenseCategoryTable.name eq name) }
                .firstOrNull() != null
        }
}