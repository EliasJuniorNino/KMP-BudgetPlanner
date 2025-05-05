package com.eliasjuniornino.budgetplanner.repositories.categories

import com.eliasjuniornino.budgetplanner.models.*
import kotlinx.coroutines.Dispatchers
import org.jetbrains.exposed.sql.and
import org.jetbrains.exposed.sql.transactions.experimental.newSuspendedTransaction

class CategoriesRepositoryImpl : CategoriesRepository {
    override suspend fun list(userId: Int): List<CategoryModel> =
        newSuspendedTransaction(Dispatchers.IO) {
            CategoryDAO
                .find { (CategoryTable.userId eq userId) and (CategoryTable.parentId eq null) }
                .map { daoToModel(it) }
        }

    override suspend fun list(userId: Int, parentCategoryId: Int): List<CategoryModel> =
        newSuspendedTransaction(Dispatchers.IO) {
            CategoryDAO
                .find { (CategoryTable.userId eq userId) and (CategoryTable.parentId eq parentCategoryId) }
                .map { daoToModel(it) }
        }

    override suspend fun store(userId: Int, data: CreateCategoryModel): CategoryModel =
        newSuspendedTransaction(Dispatchers.IO) {
            val category = CategoryDAO.new {
                user = UserDAO[userId]
                name = data.name
                color = data.color
                icon = data.icon
                parent = data.parentId?.let { CategoryDAO[it] }
            }
            daoToModel(category)
        }

    override suspend fun get(userId: Int, categoryId: Int): CategoryModel? = newSuspendedTransaction(Dispatchers.IO) {
        CategoryDAO
            .find { (CategoryTable.userId eq userId) and (CategoryTable.id eq categoryId) }
            .firstOrNull()?.let { daoToModel(it) }
    }

    override suspend fun update(userId: Int, data: CategoryModel): CategoryModel =
        newSuspendedTransaction(Dispatchers.IO) {
            val category = CategoryDAO
                .find { (CategoryTable.userId eq userId) and (CategoryTable.id eq data.id) }
                .firstOrNull() ?: throw NoSuchElementException("Category not found")

            category.name = data.name
            category.color = data.color
            category.icon = data.icon
            category.parent = data.parentId?.let { CategoryDAO[it] }

            daoToModel(category)
        }

    override suspend fun delete(userId: Int, categoryId: Int): Boolean = newSuspendedTransaction(Dispatchers.IO) {
        val category = CategoryDAO
            .find { (CategoryTable.userId eq userId) and (CategoryTable.id eq categoryId) }
            .firstOrNull()

        if (category != null) {
            category.delete()
            true
        } else {
            false
        }
    }

    override suspend fun existsByName(userId: Int, name: String): Boolean = newSuspendedTransaction(Dispatchers.IO) {
        CategoryDAO
            .find { (CategoryTable.userId eq userId) and (CategoryTable.name eq name) }
            .firstOrNull() != null
    }

}