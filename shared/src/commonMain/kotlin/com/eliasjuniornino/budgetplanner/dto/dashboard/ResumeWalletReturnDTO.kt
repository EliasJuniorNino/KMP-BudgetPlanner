package com.eliasjuniornino.budgetplanner.dto.dashboard

import kotlinx.serialization.*

@Serializable
data class ResumeWalletReturnDTO(
    val walletResume: WalletResume,
    val expensesByCategory: List<ExpenseByCategory>,
    val aiResume: AIResume
)

@Serializable
data class ExpenseByCategory(
    val expenseName: String,
    val expenseId: Int,
    val categoryName: String,
    val categoryId: Int,
    val total: Double,
    val percent: Double,
)

@Serializable
data class WalletResume(
    val totalExpenses: Double,
    val totalIncomes: Double,
)

@Serializable
data class AIResume(
    val generalMessage: String,
    val criticalAlertsCount: Int,
    val neutralAlertsCount: Int,
    val suggestionsCount: Int,
)