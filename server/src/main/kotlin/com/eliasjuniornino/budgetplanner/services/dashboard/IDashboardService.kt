package com.eliasjuniornino.budgetplanner.services.dashboard

import com.eliasjuniornino.budgetplanner.dto.dashboard.AIResume
import com.eliasjuniornino.budgetplanner.dto.dashboard.ExpenseByCategory
import com.eliasjuniornino.budgetplanner.dto.dashboard.WalletResume

interface IDashboardService {
  fun getWalletResume(): WalletResume
  fun getWalletAiResume(): AIResume
  fun getExpensesByCategory(): List<ExpenseByCategory>
}