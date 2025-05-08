package com.eliasjuniornino.budgetplanner.repositories

import com.eliasjuniornino.budgetplanner.dto.dashboard.AIResumeDTO
import com.eliasjuniornino.budgetplanner.dto.dashboard.ExpenseByCategoryDTO
import com.eliasjuniornino.budgetplanner.dto.dashboard.WalletResumeDTO

class DashboardRepositoryImpl : DashboardRepository {
    override suspend fun getAIResume(): AIResumeDTO {
        TODO("Not yet implemented")
    }

    override suspend fun getExpensesByCategory(): List<ExpenseByCategoryDTO> {
        return listOf(

        )
    }

    override suspend fun getWalletResume(): WalletResumeDTO = WalletResumeDTO(
        88.0,
        99.0
    )
}