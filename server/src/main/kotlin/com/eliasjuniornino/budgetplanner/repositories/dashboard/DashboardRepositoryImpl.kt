package com.eliasjuniornino.budgetplanner.repositories.dashboard

import com.eliasjuniornino.budgetplanner.dao.ExpenseCategoryTable
import com.eliasjuniornino.budgetplanner.dao.ExpenseTable
import com.eliasjuniornino.budgetplanner.dao.IncomeTable
import com.eliasjuniornino.budgetplanner.models.AIResumeModel
import com.eliasjuniornino.budgetplanner.models.ExpenseByCategoryModel
import com.eliasjuniornino.budgetplanner.models.WalletResumeModel
import kotlinx.coroutines.Dispatchers
import org.jetbrains.exposed.sql.IntegerColumnType
import org.jetbrains.exposed.sql.transactions.experimental.newSuspendedTransaction

class DashboardRepositoryImpl() : DashboardRepository {
    override suspend fun getWalletResume(accountId: Int): WalletResumeModel = newSuspendedTransaction(Dispatchers.IO) {
        val query = """
                SELECT
                    (SELECT SUM(e.value * e.value_multiplier)
                     FROM ${ExpenseTable.tableName} e
                     WHERE e.user_id = ?) AS total_expenses,

                    (SELECT SUM(i.value * i.value_multiplier)
                     FROM ${IncomeTable.tableName} i
                     WHERE i.user_id = ?) AS total_incomes
                """.trimIndent()
        val args = listOf(
            IntegerColumnType() to accountId,
            IntegerColumnType() to accountId
        )

        val walletResumeModel = WalletResumeModel(
            .0, .0
        )

        exec(stmt = query, args = args) { rs ->
            while (rs.next()) {
                walletResumeModel.apply {
                    totalExpenses = rs.getDouble("total_expenses")
                    totalIncomes = rs.getDouble("total_incomes")
                }
            }
        }

        walletResumeModel
    }

    override suspend fun getAiResume(accountId: Int): AIResumeModel = newSuspendedTransaction(Dispatchers.IO) {
        AIResumeModel(
            "Tudo certo por enquanto.", 0, 0, 0
        )
    }

    override suspend fun getExpensesByCategory(accountId: Int): List<ExpenseByCategoryModel> =
        newSuspendedTransaction(Dispatchers.IO) {
            val query = """
                    SELECT
                        c.name AS category_name,
                        c.id AS category_id,
                        SUM(e.value * e.value_multiplier) AS total,
                        SUM(e.value * e.value_multiplier) / NULLIF(t.total_sum, 0) * 100 AS percent
                    FROM ${ExpenseTable.tableName} e
                    JOIN ${ExpenseCategoryTable.tableName} c ON e.category_id = c.id
                    JOIN (
                        SELECT SUM(value * value_multiplier) AS total_sum
                        FROM ${ExpenseTable.tableName}
                        WHERE account_id = ?
                    ) t ON 1=1
                    WHERE e.account_id = ?
                    GROUP BY c.id, c.name, t.total_sum

                    UNION ALL

                    SELECT
                        'Outros' AS category_name,
                        NULL AS category_id,
                        SUM(e.value * e.value_multiplier) AS total,
                        SUM(e.value * e.value_multiplier) / NULLIF(t.total_sum, 0) * 100 AS percent
                    FROM ${ExpenseTable.tableName} e
                    JOIN (
                        SELECT SUM(value * value_multiplier) AS total_sum
                        FROM ${ExpenseTable.tableName}
                        WHERE account_id = ?
                    ) t ON 1=1
                    WHERE e.account_id = ? AND e.category_id IS NULL
                    GROUP BY t.total_sum
                """.trimIndent()
            val args = listOf(
                IntegerColumnType() to accountId,
                IntegerColumnType() to accountId,
                IntegerColumnType() to accountId,
                IntegerColumnType() to accountId
            )

            val results = mutableListOf<ExpenseByCategoryModel>()

            exec(stmt = query, args = args) { rs ->
                while (rs.next()) {
                    results.add(
                        ExpenseByCategoryModel(
                            categoryName = rs.getString("category_name"),
                            categoryId = rs.getInt("category_id"),
                            total = rs.getDouble("total"),
                            percent = rs.getDouble("percent")
                        )
                    )
                }
            }

            results
        }
}