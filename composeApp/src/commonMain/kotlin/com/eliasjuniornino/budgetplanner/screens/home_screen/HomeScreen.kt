package com.eliasjuniornino.budgetplanner.screens.home_screen

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.ShoppingCart
import androidx.compose.material.icons.outlined.Star
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import com.eliasjuniornino.budgetplanner.screens.home_screen.tabs.expenses_tab.ExpensesTab
import com.eliasjuniornino.budgetplanner.screens.home_screen.tabs.home_tab.HomeTab

@Composable
fun HomeScreen(
    viewModel: HomeScreenViewModel
) {
    var selectedTab by remember { mutableIntStateOf(0) }

    val tabsNames = listOf(
        "Resumo",
        "Investimentos",
        "Receitas",
        "Despesas"
    )
    val selectedIcons = listOf(
        Icons.Filled.Home,
        Icons.Filled.ShoppingCart,
        Icons.Filled.Star,
        Icons.Filled.Star
    )
    val unselectedIcons = listOf(
        Icons.Outlined.Home,
        Icons.Outlined.ShoppingCart,
        Icons.Outlined.Star,
        Icons.Outlined.Star
    )

    MaterialTheme {
        Scaffold(
            bottomBar = {
                NavigationBar {
                    tabsNames.forEachIndexed { index, tabName ->
                        NavigationBarItem(
                            icon = {
                                Icon(
                                    imageVector = if (selectedTab == index) selectedIcons[index] else unselectedIcons[index],
                                    contentDescription = tabName
                                )
                            },
                            label = { Text(tabName) },
                            selected = selectedTab == index,
                            onClick = { selectedTab = index }
                        )
                    }
                }
            }
        ) { innerPadding ->
            when (selectedTab) {
                0 -> HomeTab()
                3 -> ExpensesTab()
            }
        }
    }
}

