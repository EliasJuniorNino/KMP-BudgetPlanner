package com.eliasjuniornino.budgetplanner

import com.eliasjuniornino.budgetplanner.models.CategoryTable
import com.eliasjuniornino.budgetplanner.models.ExpenseTable
import com.eliasjuniornino.budgetplanner.models.IncomeTable
import com.eliasjuniornino.budgetplanner.models.UserTable
import io.ktor.server.application.*
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.transactions.transaction

fun Application.configureDatabase() {
    val url = getEnv("database.url", "jdbc:postgresql://localhost:5432/budgetplanner")
    val user = getEnv("database.user", "postgres")
    val password = getEnv("database.password", "password")

    Database.connect(url, user, password)

    transaction {
        SchemaUtils.createMissingTablesAndColumns(
            CategoryTable,
            ExpenseTable,
            IncomeTable,
            UserTable
        )
    }
}