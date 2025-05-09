package com.eliasjuniornino.budgetplanner.repositories

import com.eliasjuniornino.budgetplanner.dto.dashboard.AIResumeDTO
import com.eliasjuniornino.budgetplanner.dto.dashboard.ExpenseByCategoryDTO
import com.eliasjuniornino.budgetplanner.dto.dashboard.WalletResumeDTO
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.request.*

class DashboardRepositoryImpl(
    private val client: HttpClient
) : DashboardRepository {
    override suspend fun getAIResume(): AIResumeDTO {
        val response: AIResumeDTO = client.get("api/dashboard/ai-resume").body()
        return response
    }

    override suspend fun getExpensesByCategory(): List<ExpenseByCategoryDTO> {
        val response: List<ExpenseByCategoryDTO> = client.get("api/dashboard/expenses-by-category").body()
        return response
    }

    override suspend fun getWalletResume(): WalletResumeDTO {
        val response: WalletResumeDTO = client.get("api/dashboard/wallet-resume").body()
        return response
    }
}