package com.eliasjuniornino.budgetplanner.screens.home_screen.tabs.expenses_tab.components

import androidx.compose.foundation.layout.*
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.eliasjuniornino.budgetplanner.dto.expenses.ExpenseDTO
import com.eliasjuniornino.budgetplanner.dto.expenses.ExpenseType
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
fun ExpenseListItem(expense: ExpenseDTO) {
    Card(
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(text = expense.name, style = MaterialTheme.typography.titleMedium)
            Spacer(modifier = Modifier.height(4.dp))
            Text(text = expense.value.toString(), style = MaterialTheme.typography.bodyMedium)
        }
    }
}

@Preview
@Composable
private fun ExpenseItemPreview() {
    Column {
        ExpenseListItem(
            ExpenseDTO(
                0,
                "",
                ExpenseType.UNIQUE,
                .45,
                1.3,
                isDone = false,
                date = "TODO()",
                dateStart = "TODO()",
                createdAt = "TODO()",
                updatedAt = "TODO()",
                isEssential = false
            )
        )
    }
}