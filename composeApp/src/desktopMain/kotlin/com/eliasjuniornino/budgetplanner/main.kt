package com.eliasjuniornino.budgetplanner

import androidx.compose.ui.Alignment
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.WindowPosition
import androidx.compose.ui.window.application
import androidx.compose.ui.window.rememberWindowState
import moe.tlaster.precompose.ProvidePreComposeLocals

fun main() = application {
    Window(
        onCloseRequest = ::exitApplication,
        title = "BudgetPlanner",
        state = rememberWindowState(
            size = DpSize(411.dp, 800.dp),
            position = WindowPosition.Aligned(Alignment.Center)
        )
    ) {
        ProvidePreComposeLocals {
            App()
        }
    }
}