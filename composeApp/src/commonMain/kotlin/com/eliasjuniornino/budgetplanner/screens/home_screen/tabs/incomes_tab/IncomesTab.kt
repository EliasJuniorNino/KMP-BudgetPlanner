package com.eliasjuniornino.budgetplanner.screens.home_screen.tabs.incomes_tab

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import com.eliasjuniornino.budgetplanner.screens.home_screen.HomeScreenViewModel
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
fun IncomesTab(
    viewModel: HomeScreenViewModel,
    innerPadding: PaddingValues
) {
    IncomesTabView()
}

@Preview
@Composable
fun IncomesTabView(

) {
    Column {
        Text("IncomesTabView")
    }
}