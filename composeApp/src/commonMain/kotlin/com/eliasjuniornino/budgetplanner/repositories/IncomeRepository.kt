package com.eliasjuniornino.budgetplanner.repositories

import com.eliasjuniornino.budgetplanner.dto.incomes.CreateIncomesDTO
import com.eliasjuniornino.budgetplanner.dto.incomes.IncomeDTO
import com.eliasjuniornino.budgetplanner.dto.incomes.UpdateIncomesDTO

interface IncomeRepository {
    suspend fun list(): List<IncomeDTO>
    suspend fun view(id: Int): IncomeDTO
    suspend fun create(data: CreateIncomesDTO): IncomeDTO
    suspend fun update(data: UpdateIncomesDTO): IncomeDTO
    suspend fun delete(id: Int): Boolean
}