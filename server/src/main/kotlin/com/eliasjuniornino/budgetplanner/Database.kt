package com.eliasjuniornino.budgetplanner

import io.ktor.server.application.*
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.transactions.transaction
import com.eliasjuniornino.budgetplanner.models.CategoryTable
import com.eliasjuniornino.budgetplanner.models.ExpenseTable
import com.eliasjuniornino.budgetplanner.models.IncomeTable
import com.eliasjuniornino.budgetplanner.models.UserTable

fun Application.configureDatabase() {
    val url = getEnv("database.url", "jdbc:postgresql://localhost:5432/budgetplanner")
    val user = getEnv("database.user", "admin")
    val password = getEnv("database.password", "admin")

    Database.connect(
        url = url,
        driver = "org.postgresql.Driver",
        user = user,
        password = password
    )

    transaction {
        SchemaUtils.createMissingTablesAndColumns(
            CategoryTable,
            ExpenseTable,
            IncomeTable,
            UserTable
        )
    }
}