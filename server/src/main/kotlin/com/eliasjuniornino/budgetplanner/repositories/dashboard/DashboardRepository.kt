package com.eliasjuniornino.budgetplanner.repositories.dashboard

import com.eliasjuniornino.budgetplanner.models.AIResumeModel
import com.eliasjuniornino.budgetplanner.models.ExpenseByCategoryModel
import com.eliasjuniornino.budgetplanner.models.WalletResumeModel

interface DashboardRepository {
    suspend fun getWalletResume(userId: Int): WalletResumeModel
    suspend fun getAiResume(userId: Int): AIResumeModel
    suspend fun getExpensesByCategory(userId: Int): List<ExpenseByCategoryModel>
}