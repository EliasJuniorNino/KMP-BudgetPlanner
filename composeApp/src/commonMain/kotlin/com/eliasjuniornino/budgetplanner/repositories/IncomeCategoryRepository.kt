package com.eliasjuniornino.budgetplanner.repositories

import com.eliasjuniornino.budgetplanner.dto.income_categories.IncomeCategoryDTO
import com.eliasjuniornino.budgetplanner.dto.income_categories.UpdateIncomeCategoryDTO
import com.eliasjuniornino.budgetplanner.dto.income_categories.CreateIncomeCategoryDTO

interface IncomeCategoryRepository {
    suspend fun list(): List<IncomeCategoryDTO>
    suspend fun listSub(id: Int): List<IncomeCategoryDTO>
    suspend fun view(id: Int): IncomeCategoryDTO
    suspend fun create(data: CreateIncomeCategoryDTO): IncomeCategoryDTO
    suspend fun update(data: UpdateIncomeCategoryDTO): IncomeCategoryDTO
    suspend fun delete(id: Int): Boolean
}