package com.eliasjuniornino.budgetplanner.database

import com.eliasjuniornino.budgetplanner.dao.AccountTable
import com.eliasjuniornino.budgetplanner.dto.expenses.ExpenseType
import com.eliasjuniornino.budgetplanner.dto.expenses.FrequencyType
import com.eliasjuniornino.budgetplanner.models.CreateExpenseCategoryModel
import com.eliasjuniornino.budgetplanner.models.CreateExpenseModel
import com.eliasjuniornino.budgetplanner.dao.ExpenseCategoryTable
import com.eliasjuniornino.budgetplanner.dao.ExpenseTable
import com.eliasjuniornino.budgetplanner.dao.IncomeCategoryTable
import com.eliasjuniornino.budgetplanner.dao.UserTable
import org.jetbrains.exposed.sql.batchInsert
import org.jetbrains.exposed.sql.insertAndGetId
import org.jetbrains.exposed.sql.selectAll
import org.jetbrains.exposed.sql.transactions.transaction

fun seedDatabase() {
    transaction {
        if (UserTable.selectAll().empty()) {
            val eliasId = UserTable.insertAndGetId {
                it[email] = "elias@example.com"
                it[name] = "Elias"
                it[password] = "senha123"
            }.value
            val wellId = UserTable.insertAndGetId {
                it[email] = "wellinton@example.com"
                it[name] = "Wellinton"
                it[password] = "senha123"
            }.value
            val accountId = AccountTable.insertAndGetId {
                it[name] = "Casa"
            }.value

            // Expense Category
            val expenseId = ExpenseCategoryTable.insertAndGetId {
                it[ExpenseCategoryTable.accountId] = accountId
                it[name] = "Alimentação"
                it[color] = "#000000"
            }.value
            ExpenseCategoryTable.batchInsert(listOf(
                CreateExpenseCategoryModel(eliasId, "Mercado", "#000000", parentId = expenseId),
                CreateExpenseCategoryModel(eliasId, "Açougue", "#000000", parentId = expenseId),
                CreateExpenseCategoryModel(eliasId, "Transporte", "#00FF00"),
                CreateExpenseCategoryModel(eliasId, "Moradia", "#FF0000"),
                CreateExpenseCategoryModel(eliasId, "Lazer", "#FF0000"),
                CreateExpenseCategoryModel(eliasId, "Educação", "#FF0000"),
                CreateExpenseCategoryModel(eliasId, "Saude", "#FF0000"),
            )) { category ->
                this[ExpenseCategoryTable.accountId] = category.accountId
                this[ExpenseCategoryTable.name] = category.name
                this[ExpenseCategoryTable.color] = category.color
                this[ExpenseCategoryTable.parentId] = category.parentId
            }

            // Income Category
            IncomeCategoryTable.batchInsert(listOf(
                CreateExpenseCategoryModel(eliasId, "Salario", "#000000"),
                CreateExpenseCategoryModel(eliasId, "Renda Extra", "#000000")
            )) { category ->
                this[ExpenseCategoryTable.accountId] = category.accountId
                this[ExpenseCategoryTable.name] = category.name
                this[ExpenseCategoryTable.color] = category.color
            }

            // Income Category
            ExpenseTable.batchInsert(listOf(
                CreateExpenseModel(eliasId, "Gastos Fim Semana",
                    value = 140.0,
                    valueMultiplier = 4.0,
                    expenseType = ExpenseType.FREQUENCY,
                    frequencyType = FrequencyType.WEEKLY
                ),
                CreateExpenseModel(eliasId, "Mistura da Semana",
                    value = 250.0,
                    valueMultiplier = 4.0,
                    expenseType = ExpenseType.FREQUENCY,
                    frequencyType = FrequencyType.WEEKLY,
                    isEssential = true
                ),
                CreateExpenseModel(eliasId, "Padaria",
                    value = 30.0,
                    valueMultiplier = 31.0,
                    expenseType = ExpenseType.FREQUENCY,
                    frequencyType = FrequencyType.DAILY,
                    isEssential = true
                ),
                CreateExpenseModel(eliasId, "Cabelereiro",
                    value = 50.0,
                    valueMultiplier = 2.0,
                    expenseType = ExpenseType.FREQUENCY,
                    frequencyType = FrequencyType.MONTHLY,
                    isEssential = true
                ),
                CreateExpenseModel(eliasId, "Plano de Saude",
                    value = 300.0,
                    valueMultiplier = 2.0,
                    expenseType = ExpenseType.FREQUENCY,
                    frequencyType = FrequencyType.MONTHLY,
                    isEssential = true
                ),
                CreateExpenseModel(eliasId, "Internet", value = 159.0),
                CreateExpenseModel(eliasId, "Energia", value = 600.0),
                CreateExpenseModel(eliasId, "Água", value = 300.0),
                CreateExpenseModel(eliasId, "Aluguel", value = 1500.0),
            )) { category ->
                this[ExpenseTable.accountId] = category.accountId
                this[ExpenseTable.name] = category.name
                this[ExpenseTable.expenseType] = category.expenseType
                this[ExpenseTable.categoryId] = category.categoryId
                this[ExpenseTable.subCategoryId] = category.subCategoryId
            }
        }
    }
}
