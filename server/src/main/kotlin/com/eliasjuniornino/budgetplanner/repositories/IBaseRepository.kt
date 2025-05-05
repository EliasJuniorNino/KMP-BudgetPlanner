package com.eliasjuniornino.budgetplanner.repositories

interface IBaseRepository<CreateModel, UpdateModel, Model> {
    suspend fun list(userId: Int): List<Model>
    suspend fun store(userId: Int, data: CreateModel): Model
    suspend fun get(userId: Int, id: Int): Model?
    suspend fun update(userId: Int, data: UpdateModel): Model
    suspend fun delete(userId: Int, id: Int): Boolean
}