package com.eliasjuniornino.budgetplanner.services.dashboard

import com.eliasjuniornino.budgetplanner.dto.dashboard.AIResume
import com.eliasjuniornino.budgetplanner.dto.dashboard.ExpenseByCategory
import com.eliasjuniornino.budgetplanner.dto.dashboard.WalletResume

class DashboardService(

) : IDashboardService {
  override fun getWalletResume() = WalletResume(
    totalExpenses = 0.0,
    totalIncomes = 0.0
  )

  override fun getWalletAiResume() = AIResume(
    generalMessage = "Tudo ok por enquanto, parabens!",
    criticalAlertsCount = 0,
    neutralAlertsCount = 0,
    suggestionsCount = 0
  )

  override fun getExpensesByCategory() = listOf(
    ExpenseByCategory(
      expenseName = "Expense Name 1",
      expenseId = 0,
      categoryName = "Category Name 1",
      categoryId = 0,
      total = 75.8,
      percent = 75.6
    ),
    ExpenseByCategory(
      expenseName = "Expense Name 2",
      expenseId = 1,
      categoryName = "Category Name 2",
      categoryId = 1,
      total = 50.8,
      percent = 50.6
    )
  )
}