package com.eliasjuniornino.budgetplanner.screens.home_screen.tabs.expenses_tab

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.eliasjuniornino.budgetplanner.dto.expenses.ExpenseDTO
import com.eliasjuniornino.budgetplanner.dto.expenses.ExpenseType
import com.eliasjuniornino.budgetplanner.screens.home_screen.HomeScreenViewModel
import com.eliasjuniornino.budgetplanner.screens.home_screen.tabs.expenses_tab.components.ExpenseListItem
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
fun ExpensesTab(
    viewModel: HomeScreenViewModel,
    innerPadding: PaddingValues
) {
    val expenses by viewModel.expenses.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.fetchExpensesData()
    }

    if (expenses != null) {
        ExpenseTabView(
            expenses.orEmpty(),
            innerPadding
        )
    }
}

@Composable
fun ExpenseTabView(
    expenses: List<ExpenseDTO>,
    innerPadding: PaddingValues
) {
    LazyColumn(
        contentPadding = innerPadding,
        verticalArrangement = Arrangement.spacedBy(8.dp),
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        items(expenses) { expense ->
            ExpenseListItem(expense)
        }
    }
}

@Preview
@Composable
private fun ExpenseTabViewPreview() {
    ExpenseTabView(
        expenses = listOf(
            ExpenseDTO(
                id = 0,
                name = "Teste",
                expenseType = ExpenseType.UNIQUE,
                value = 55.45,
                valueMultiplier = 1.0,
                isDone = false,
                date = "TODO()",
                dateStart = "TODO()",
                createdAt = "TODO()",
                updatedAt = "TODO()",
                isEssential = false
            )
        ),
        innerPadding = PaddingValues()
    )
}
