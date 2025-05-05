package com.eliasjuniornino.budgetplanner.repositories

interface IBaseRepository<T> {
    suspend fun list(userId: Int): List<T>
    suspend fun store(userId: Int, data: T): T
    suspend fun get(userId: Int, id: Int): T?
    suspend fun update(userId: Int, data: T): T
    suspend fun delete(userId: Int, id: Int): Boolean
}