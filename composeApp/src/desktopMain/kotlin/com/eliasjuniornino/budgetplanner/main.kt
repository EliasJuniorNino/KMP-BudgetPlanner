package com.eliasjuniornino.budgetplanner

import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import moe.tlaster.precompose.ProvidePreComposeLocals

fun main() = application {
    Window(
        onCloseRequest = ::exitApplication,
        title = "BudgetPlanner",
    ) {
        ProvidePreComposeLocals {
            App()
        }
    }
}