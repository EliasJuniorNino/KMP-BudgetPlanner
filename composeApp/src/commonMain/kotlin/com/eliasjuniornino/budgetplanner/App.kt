package com.eliasjuniornino.budgetplanner

import androidx.compose.runtime.*
import com.eliasjuniornino.budgetplanner.repositories.DashboardRepositoryImpl
import com.eliasjuniornino.budgetplanner.repositories.ExpenseCategoryRepositoryImpl
import com.eliasjuniornino.budgetplanner.repositories.ExpenseRepositoryImpl
import com.eliasjuniornino.budgetplanner.repositories.IncomeCategoryRepositoryImpl
import com.eliasjuniornino.budgetplanner.repositories.IncomeRepositoryImpl
import com.eliasjuniornino.budgetplanner.repositories.UserRepositoryImpl
import com.eliasjuniornino.budgetplanner.screens.home_screen.HomeScreen
import com.eliasjuniornino.budgetplanner.screens.home_screen.HomeScreenViewModel
import moe.tlaster.precompose.PreComposeApp
import moe.tlaster.precompose.navigation.NavHost
import moe.tlaster.precompose.navigation.rememberNavigator
import moe.tlaster.precompose.navigation.transition.NavTransition
import moe.tlaster.precompose.viewmodel.viewModel

@Composable
fun App() {
    val client = AppHttpClient.getClient()

    PreComposeApp {
        val navigator = rememberNavigator()
        val navTransition = NavTransition()
        NavHost(
            navigator = navigator,
            navTransition = navTransition,
            initialRoute = "/home"
        ) {
            scene(
                route = "/home",
                navTransition = navTransition
            ) {
                val viewModel = viewModel {
                    HomeScreenViewModel(
                        dashboardRepository = DashboardRepositoryImpl(client),
                        incomesRepository = IncomeRepositoryImpl(client),
                        expensesRepository = ExpenseRepositoryImpl(client),
                        incomeCategoryRepository = IncomeCategoryRepositoryImpl(client),
                        expenseCategoryRepository = ExpenseCategoryRepositoryImpl(client),
                        userRepository = UserRepositoryImpl(client),
                    )
                }
                HomeScreen(viewModel = viewModel)
            }
        }
    }
}