package com.eliasjuniornino.budgetplanner.database

import com.eliasjuniornino.budgetplanner.dao.AccountTable
import com.eliasjuniornino.budgetplanner.dao.AccountUserTable
import com.eliasjuniornino.budgetplanner.dto.expenses.ExpenseType
import com.eliasjuniornino.budgetplanner.dto.expenses.FrequencyType
import com.eliasjuniornino.budgetplanner.models.CreateExpenseModel
import com.eliasjuniornino.budgetplanner.dao.ExpenseCategoryTable
import com.eliasjuniornino.budgetplanner.dao.ExpenseTable
import com.eliasjuniornino.budgetplanner.dao.IncomeCategoryTable
import com.eliasjuniornino.budgetplanner.dao.IncomeTable
import com.eliasjuniornino.budgetplanner.dao.UserTable
import com.eliasjuniornino.budgetplanner.models.CreateAccountUserModel
import com.eliasjuniornino.budgetplanner.models.CreateIncomeModel
import org.jetbrains.exposed.sql.batchInsert
import org.jetbrains.exposed.sql.insertAndGetId
import org.jetbrains.exposed.sql.selectAll
import org.jetbrains.exposed.sql.transactions.transaction
import org.mindrot.jbcrypt.BCrypt

fun seedDatabase() {
    transaction {
        if (UserTable.selectAll().empty()) {
            val eliasId = UserTable.insertAndGetId {
                it[email] = "elias@example.com"
                it[name] = "Elias"
                it[password] = BCrypt.hashpw("senha123", BCrypt.gensalt())
            }.value
            val wellId = UserTable.insertAndGetId {
                it[email] = "wellinton@example.com"
                it[name] = "Wellinton"
                it[password] = BCrypt.hashpw("senha123", BCrypt.gensalt())
            }.value
            // Account
            val account1Id = AccountTable.insertAndGetId {
                it[name] = "Casa"
                it[userId] = eliasId
            }.value
            AccountUserTable.batchInsert(listOf(
                CreateAccountUserModel(account1Id, eliasId),
                CreateAccountUserModel(account1Id, wellId),
            )) { item ->
                this[AccountUserTable.accountId] = item.accountId
                this[AccountUserTable.userId] = item.userId
            }

            // Expense Category
            val alimentacaoId = ExpenseCategoryTable.insertAndGetId {
                it[userId] = eliasId
                it[accountId] = account1Id
                it[name] = "Alimentação"
                it[color] = "#000000"
            }.value
            val mercadoId = ExpenseCategoryTable.insertAndGetId {
                it[userId] = eliasId
                it[accountId] = account1Id
                it[name] = "Mercado"
                it[color] = "#000000"
                it[parentId] = alimentacaoId
            }.value
            val acougueId = ExpenseCategoryTable.insertAndGetId {
                it[userId] = eliasId
                it[accountId] = account1Id
                it[name] = "Açougue"
                it[color] = "#000000"
                it[parentId] = alimentacaoId
            }.value
            val transporteId = ExpenseCategoryTable.insertAndGetId {
                it[userId] = eliasId
                it[accountId] = account1Id
                it[name] = "Transporte"
                it[color] = "#000000"
            }.value
            val moradiaId = ExpenseCategoryTable.insertAndGetId {
                it[userId] = eliasId
                it[accountId] = account1Id
                it[name] = "Moradia"
                it[color] = "#000000"
            }.value
            val lazerId = ExpenseCategoryTable.insertAndGetId {
                it[userId] = eliasId
                it[accountId] = account1Id
                it[name] = "Lazer"
                it[color] = "#000000"
            }.value
            val educacaoId = ExpenseCategoryTable.insertAndGetId {
                it[userId] = eliasId
                it[accountId] = account1Id
                it[name] = "Educação"
                it[color] = "#000000"
            }.value
            val saudeId = ExpenseCategoryTable.insertAndGetId {
                it[userId] = eliasId
                it[accountId] = account1Id
                it[name] = "Saude"
                it[color] = "#000000"
            }.value
            val vestuarioId = ExpenseCategoryTable.insertAndGetId {
                it[userId] = eliasId
                it[accountId] = account1Id
                it[name] = "Vestuario"
                it[color] = "#000000"
            }.value
            val fixaId = ExpenseCategoryTable.insertAndGetId {
                it[userId] = eliasId
                it[accountId] = account1Id
                it[name] = "Despesa fixa"
                it[color] = "#000000"
            }.value
            val emprestimoId = ExpenseCategoryTable.insertAndGetId {
                it[userId] = eliasId
                it[accountId] = account1Id
                it[name] = "Emprestimo"
                it[color] = "#000000"
            }.value

            // Income Category
            val salarioId = IncomeCategoryTable.insertAndGetId {
                it[userId] = eliasId
                it[accountId] = account1Id
                it[name] = "Salario"
                it[color] = "000000"
            }
            val valeId = IncomeCategoryTable.insertAndGetId {
                it[userId] = eliasId
                it[accountId] = account1Id
                it[name] = "Vale Refeição"
                it[color] = "000000"
            }

            // Incomes
            IncomeTable.batchInsert(listOf(
                CreateIncomeModel(account1Id, "Renda 1", value = 4150.0),
                CreateIncomeModel(account1Id, "VA 1", value = 600.0),
            )) { item ->
                this[IncomeTable.userId] = eliasId
                this[IncomeTable.accountId] = item.accountId
                this[IncomeTable.name] = item.name
                this[IncomeTable.value] = item.value
                this[IncomeTable.valueMultiplier] = item.valueMultiplier
                this[IncomeTable.incomeType] = item.incomeType
                this[IncomeTable.categoryId] = item.categoryId
                this[IncomeTable.subCategoryId] = item.subCategoryId
            }
            IncomeTable.batchInsert(listOf(
                CreateIncomeModel(account1Id, "Renda 2", value = 3600.0),
                CreateIncomeModel(account1Id, "VA 2", value = 300.0),
            )) { item ->
                this[IncomeTable.userId] = wellId
                this[IncomeTable.accountId] = item.accountId
                this[IncomeTable.name] = item.name
                this[IncomeTable.value] = item.value
                this[IncomeTable.valueMultiplier] = item.valueMultiplier
                this[IncomeTable.incomeType] = item.incomeType
                this[IncomeTable.categoryId] = item.categoryId
                this[IncomeTable.subCategoryId] = item.subCategoryId
            }

            // Expenses
            ExpenseTable.batchInsert(listOf(
                CreateExpenseModel(account1Id, "Mercado", value = 1000.0,
                    expenseType = ExpenseType.FREQUENCY,
                    frequencyType = FrequencyType.MONTHLY,
                    categoryId = alimentacaoId,
                    subCategoryId = mercadoId,
                    isEssential = true
                ),
                CreateExpenseModel(account1Id, "Gastos Fim Semana",
                    value = 140.0,
                    valueMultiplier = 4.0,
                    categoryId = lazerId,
                    expenseType = ExpenseType.FREQUENCY,
                    frequencyType = FrequencyType.WEEKLY
                ),
                CreateExpenseModel(account1Id, "Mistura da Semana",
                    value = 250.0,
                    valueMultiplier = 4.0,
                    categoryId = alimentacaoId,
                    subCategoryId = mercadoId,
                    expenseType = ExpenseType.FREQUENCY,
                    frequencyType = FrequencyType.WEEKLY,
                    isEssential = true
                ),
                CreateExpenseModel(account1Id, "Padaria",
                    value = 30.0,
                    valueMultiplier = 31.0,
                    categoryId = alimentacaoId,
                    subCategoryId = mercadoId,
                    expenseType = ExpenseType.FREQUENCY,
                    frequencyType = FrequencyType.DAILY,
                    isEssential = true
                ),
                CreateExpenseModel(account1Id, "Cabelereiro",
                    value = 50.0,
                    valueMultiplier = 2.0,
                    categoryId = vestuarioId,
                    expenseType = ExpenseType.FREQUENCY,
                    frequencyType = FrequencyType.MONTHLY,
                    isEssential = true
                ),
                CreateExpenseModel(account1Id, "Plano de Saude",
                    value = 40.0,
                    valueMultiplier = 2.0,
                    categoryId = saudeId,
                    expenseType = ExpenseType.FREQUENCY,
                    frequencyType = FrequencyType.MONTHLY,
                    isEssential = true
                ),
                CreateExpenseModel(account1Id, "Internet", value = 159.0,
                    expenseType = ExpenseType.FREQUENCY,
                    frequencyType = FrequencyType.MONTHLY,
                    categoryId = fixaId,
                    isEssential = true
                ),
                CreateExpenseModel(account1Id, "Energia", value = 600.0,
                    expenseType = ExpenseType.FREQUENCY,
                    frequencyType = FrequencyType.MONTHLY,
                    categoryId = fixaId,
                    isEssential = true
                ),
                CreateExpenseModel(account1Id, "Água", value = 300.0,
                    expenseType = ExpenseType.FREQUENCY,
                    frequencyType = FrequencyType.MONTHLY,
                    categoryId = fixaId,
                    isEssential = true
                ),
                CreateExpenseModel(account1Id, "Aluguel", value = 1500.0,
                    expenseType = ExpenseType.FREQUENCY,
                    frequencyType = FrequencyType.MONTHLY,
                    categoryId = fixaId,
                    isEssential = true
                ),
                CreateExpenseModel(account1Id, "Combustivel", value = 30.0,
                    valueMultiplier = 4.0,
                    expenseType = ExpenseType.FREQUENCY,
                    frequencyType = FrequencyType.WEEKLY,
                    categoryId = transporteId,
                    isEssential = true
                ),
                CreateExpenseModel(account1Id, "Maquina de lavar roupa", value = 400.0,
                    valueMultiplier = 1.0,
                    expenseType = ExpenseType.PARCEL,
                    frequencyType = FrequencyType.MONTHLY,
                    categoryId = emprestimoId,
                    isEssential = true
                ),
            )) { item ->
                this[ExpenseTable.userId] = eliasId
                this[ExpenseTable.accountId] = item.accountId
                this[ExpenseTable.name] = item.name
                this[ExpenseTable.value] = item.value
                this[ExpenseTable.valueMultiplier] = item.valueMultiplier
                this[ExpenseTable.expenseType] = item.expenseType
                this[ExpenseTable.frequencyType] = item.frequencyType
                this[ExpenseTable.categoryId] = item.categoryId
                this[ExpenseTable.subCategoryId] = item.subCategoryId
                this[ExpenseTable.isEssential] = item.isEssential
            }
        }
    }
}
