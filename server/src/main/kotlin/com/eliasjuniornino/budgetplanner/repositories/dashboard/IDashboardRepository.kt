package com.eliasjuniornino.budgetplanner.repositories.dashboard

import com.eliasjuniornino.budgetplanner.dto.dashboard.AIResume
import com.eliasjuniornino.budgetplanner.dto.dashboard.ExpenseByCategory
import com.eliasjuniornino.budgetplanner.dto.dashboard.WalletResume

interface IDashboardRepository {
    fun getWalletResume(): WalletResume
    fun getWalletAiResume(): AIResume
    fun getExpensesByCategory(): List<ExpenseByCategory>
}