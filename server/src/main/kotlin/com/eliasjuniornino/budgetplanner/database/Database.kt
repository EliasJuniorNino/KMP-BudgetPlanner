package com.eliasjuniornino.budgetplanner.database

import com.eliasjuniornino.budgetplanner.dao.AccountTable
import com.eliasjuniornino.budgetplanner.dao.AccountUserTable
import com.eliasjuniornino.budgetplanner.getEnv
import io.ktor.server.application.*
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.transactions.transaction
import com.eliasjuniornino.budgetplanner.dao.ExpenseCategoryTable
import com.eliasjuniornino.budgetplanner.dao.ExpenseTable
import com.eliasjuniornino.budgetplanner.dao.IncomeCategoryTable
import com.eliasjuniornino.budgetplanner.dao.IncomeTable
import com.eliasjuniornino.budgetplanner.dao.UserTable

fun Application.configureDatabase() {
    val url = getEnv("database.url", "jdbc:postgresql://localhost:5432/budgetplanner")
    val user = getEnv("database.user", "admin")
    val password = getEnv("database.password", "admin")
    val environment = getEnv("ENV", "development")

    Database.connect(
        url = url,
        driver = "org.postgresql.Driver",
        user = user,
        password = password
    )

    transaction {
        SchemaUtils.createMissingTablesAndColumns(
            AccountTable,
            AccountUserTable,
            ExpenseCategoryTable,
            ExpenseTable,
            IncomeCategoryTable,
            IncomeTable,
            UserTable
        )
    }

    if (environment == "development") {
        seedDatabase()
    }
}