package com.eliasjuniornino.budgetplanner.repositories.incomes_categories

import com.eliasjuniornino.budgetplanner.models.IncomeCategoryModel
import com.eliasjuniornino.budgetplanner.models.CreateIncomeCategoryModel
import com.eliasjuniornino.budgetplanner.dao.IncomeCategoryDAO
import com.eliasjuniornino.budgetplanner.dao.IncomeCategoryTable
import com.eliasjuniornino.budgetplanner.dao.UserDAO
import com.eliasjuniornino.budgetplanner.dao.daoToModel
import kotlinx.coroutines.Dispatchers
import org.jetbrains.exposed.sql.and
import org.jetbrains.exposed.sql.transactions.experimental.newSuspendedTransaction

class IncomeCategoriesRepositoryImpl : IncomeCategoriesRepository {
    override suspend fun list(userId: Int): List<IncomeCategoryModel> =
        newSuspendedTransaction(Dispatchers.IO) {
            IncomeCategoryDAO
                .find { (IncomeCategoryTable.userId eq userId) and (IncomeCategoryTable.parentId eq null) }
                .map { daoToModel(it) }
        }

    override suspend fun list(userId: Int, parentCategoryId: Int): List<IncomeCategoryModel> =
        newSuspendedTransaction(Dispatchers.IO) {
            IncomeCategoryDAO
                .find { (IncomeCategoryTable.userId eq userId) and (IncomeCategoryTable.parentId eq parentCategoryId) }
                .map { daoToModel(it) }
        }

    override suspend fun store(userId: Int, data: CreateIncomeCategoryModel): IncomeCategoryModel =
        newSuspendedTransaction(Dispatchers.IO) {
            val category = IncomeCategoryDAO.new {
                user = UserDAO[userId]
                name = data.name
                color = data.color
                icon = data.icon
                parent = data.parentId?.let { IncomeCategoryDAO[it] }
            }
            daoToModel(category)
        }

    override suspend fun get(userId: Int, categoryId: Int): IncomeCategoryModel? =
        newSuspendedTransaction(Dispatchers.IO) {
            IncomeCategoryDAO
                .find { (IncomeCategoryTable.userId eq userId) and (IncomeCategoryTable.id eq categoryId) }
                .firstOrNull()?.let { daoToModel(it) }
        }

    override suspend fun update(userId: Int, data: IncomeCategoryModel): IncomeCategoryModel =
        newSuspendedTransaction(Dispatchers.IO) {
            val category = IncomeCategoryDAO
                .find { (IncomeCategoryTable.userId eq userId) and (IncomeCategoryTable.id eq data.id) }
                .firstOrNull() ?: throw NoSuchElementException("Category not found")

            category.name = data.name
            category.color = data.color
            category.icon = data.icon
            category.parent = data.parentId?.let { IncomeCategoryDAO[it] }

            daoToModel(category)
        }

    override suspend fun delete(userId: Int, categoryId: Int): Boolean =
        newSuspendedTransaction(Dispatchers.IO) {
            val category = IncomeCategoryDAO
                .find { (IncomeCategoryTable.userId eq userId) and (IncomeCategoryTable.id eq categoryId) }
                .firstOrNull()

            if (category != null) {
                category.delete()
                true
            } else {
                false
            }
        }

    override suspend fun existsByName(userId: Int, name: String): Boolean =
        newSuspendedTransaction(Dispatchers.IO) {
            IncomeCategoryDAO
                .find { (IncomeCategoryTable.userId eq userId) and (IncomeCategoryTable.name eq name) }
                .firstOrNull() != null
        }
}