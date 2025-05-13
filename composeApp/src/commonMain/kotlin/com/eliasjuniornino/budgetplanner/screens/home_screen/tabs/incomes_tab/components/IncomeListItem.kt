package com.eliasjuniornino.budgetplanner.screens.home_screen.tabs.incomes_tab.components

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
import com.eliasjuniornino.budgetplanner.dto.incomes.IncomeDTO
import com.eliasjuniornino.budgetplanner.extensions.toMoneyStr
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
fun IncomeListItem(income: IncomeDTO) {
    val valueStr = (income.value * income.valueMultiplier).toMoneyStr()
    Card(
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(text = income.name, style = MaterialTheme.typography.titleMedium)
            Spacer(modifier = Modifier.height(4.dp))
            Text(text = valueStr, style = MaterialTheme.typography.bodyMedium)
        }
    }
}
