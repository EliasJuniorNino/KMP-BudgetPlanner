package com.eliasjuniornino.budgetplanner.repositories.dashboard

import com.eliasjuniornino.budgetplanner.models.AIResumeModel
import com.eliasjuniornino.budgetplanner.models.ExpenseByCategoryModel
import com.eliasjuniornino.budgetplanner.models.WalletResumeModel

interface DashboardRepository {
    suspend fun getWalletResume(accountId: Int): WalletResumeModel
    suspend fun getAiResume(accountId: Int): AIResumeModel
    suspend fun getExpensesByCategory(accountId: Int): List<ExpenseByCategoryModel>
}