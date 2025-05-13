package com.eliasjuniornino.budgetplanner.screens.home_screen.tabs.incomes_tab

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.eliasjuniornino.budgetplanner.dto.incomes.IncomeDTO
import com.eliasjuniornino.budgetplanner.screens.home_screen.HomeScreenViewModel
import com.eliasjuniornino.budgetplanner.screens.home_screen.tabs.incomes_tab.components.IncomeListItem
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
fun IncomesTab(
    viewModel: HomeScreenViewModel,
    innerPadding: PaddingValues
) {
    val incomes by viewModel.incomes.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.fetchIncomesData()
    }

    if (incomes != null) {
        IncomesTabView(
            incomes.orEmpty(),
            innerPadding
        )
    }
}

@Preview
@Composable
fun IncomesTabView(
    incomes: List<IncomeDTO>,
    innerPadding: PaddingValues
) {
    LazyColumn(
        contentPadding = innerPadding,
        verticalArrangement = Arrangement.spacedBy(8.dp),
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        items(incomes) { income ->
            IncomeListItem(income)
        }
    }
}