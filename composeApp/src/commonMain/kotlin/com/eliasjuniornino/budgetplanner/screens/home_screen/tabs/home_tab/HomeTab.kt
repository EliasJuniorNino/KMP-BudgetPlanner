package com.eliasjuniornino.budgetplanner.screens.home_screen.tabs.home_tab

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.eliasjuniornino.budgetplanner.dto.dashboard.AIResumeDTO
import com.eliasjuniornino.budgetplanner.dto.dashboard.ExpenseByCategoryDTO
import com.eliasjuniornino.budgetplanner.dto.dashboard.WalletResumeDTO
import com.eliasjuniornino.budgetplanner.screens.home_screen.HomeScreenViewModel
import com.eliasjuniornino.budgetplanner.screens.home_screen.tabs.home_tab.components.CategoryBarChart
import com.eliasjuniornino.budgetplanner.screens.home_screen.tabs.home_tab.components.Header
import com.eliasjuniornino.budgetplanner.screens.home_screen.tabs.home_tab.components.RobotFeedback
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
fun HomeTab(
    viewModel: HomeScreenViewModel,
    innerPadding: PaddingValues
) {
    val walletResume by viewModel.walletResume.collectAsState()
    val aiResume by viewModel.aiResume.collectAsState()
    val expensesByCategory by viewModel.expensesByCategory.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.fetchHomeData()
    }

    if (walletResume != null && aiResume != null && expensesByCategory != null) {
        HomeTabView(
            walletResume!!,
            aiResume!!,
            expensesByCategory.orEmpty()
        )
    }
}

@Composable
fun HomeTabView(
    walletResume: WalletResumeDTO,
    aiResume: AIResumeDTO,
    expensesByCategory: List<ExpenseByCategoryDTO>
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Header(walletResume)
        RobotFeedback(aiResume)
        CategoryBarChart(expensesByCategory)
    }
}