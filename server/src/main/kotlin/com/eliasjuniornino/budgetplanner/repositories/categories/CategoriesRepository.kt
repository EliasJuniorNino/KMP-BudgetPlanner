package com.eliasjuniornino.budgetplanner.repositories.categories

import com.eliasjuniornino.budgetplanner.models.*
import kotlinx.coroutines.Dispatchers
import org.jetbrains.exposed.sql.and
import org.jetbrains.exposed.sql.transactions.experimental.newSuspendedTransaction

class CategoriesRepository() : ICategoriesRepository {
    override suspend fun list(userId: Int): List<CategoryModel> = newSuspendedTransaction(Dispatchers.IO) {
        CategoryDAO.find { CategoryTable.userId eq userId }.map { daoToModel(it) }
    }

    override suspend fun store(userId: Int, data: CategoryModel): CategoryModel =
        newSuspendedTransaction(Dispatchers.IO) {
            val category = CategoryDAO.new {
                user = UserDAO[userId]
                name = data.name
                color = data.color
                icon = data.icon
                parentId = data.parentId
            }
            daoToModel(category)
        }

    override suspend fun get(userId: Int, id: Int): CategoryModel? = newSuspendedTransaction(Dispatchers.IO) {
        CategoryDAO
            .find { (CategoryTable.userId eq userId) and (CategoryTable.id eq id) }
            .firstOrNull()?.let { daoToModel(it) }
    }

    override suspend fun update(userId: Int, data: CategoryModel): CategoryModel {
        TODO("Not yet implemented")
    }

    override suspend fun delete(userId: Int, id: Int): Boolean {
        TODO("Not yet implemented")
    }

    override suspend fun existsByName(userId: Int, name: String): Boolean = newSuspendedTransaction(Dispatchers.IO) {
        CategoryDAO
            .find { (CategoryTable.userId eq userId) and (CategoryTable.name eq name) }
            .firstOrNull() != null
    }

}