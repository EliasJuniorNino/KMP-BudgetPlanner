package com.eliasjuniornino.budgetplanner.repositories

import com.eliasjuniornino.budgetplanner.dto.dashboard.AIResumeDTO
import com.eliasjuniornino.budgetplanner.dto.dashboard.ExpenseByCategoryDTO
import com.eliasjuniornino.budgetplanner.dto.dashboard.WalletResumeDTO

interface DashboardRepository {
    suspend fun getAIResume(): AIResumeDTO
    suspend fun getExpensesByCategory(): List<ExpenseByCategoryDTO>
    suspend fun getWalletResume(): WalletResumeDTO
}