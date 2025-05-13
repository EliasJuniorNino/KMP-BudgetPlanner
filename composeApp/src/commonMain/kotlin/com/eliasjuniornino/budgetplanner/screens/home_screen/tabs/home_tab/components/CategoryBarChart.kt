package com.eliasjuniornino.budgetplanner.screens.home_screen.tabs.home_tab.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.eliasjuniornino.budgetplanner.dto.dashboard.ExpenseByCategoryDTO

@Composable
fun CategoryBarChart(
    expensesByCategory: List<ExpenseByCategoryDTO>
) {
    val values = listOf(
        3844f to Color(0xFF003366),
        2624f to Color(0xFF4B4BAF),
        1026.1f to Color.Red,
        438f to Color(0xFFFF69B4),
        320f to Color.Cyan,
        100f to Color(0xFF2196F3)
    )

    Column(
        Modifier
            .fillMaxWidth()
    ) {
        Text("Despesas por categoria", fontWeight = FontWeight.SemiBold)

        Spacer(Modifier.height(16.dp))

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(150.dp),
            verticalAlignment = Alignment.Bottom,
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            val max = values.maxOf { it.first }

            values.forEach { (amount, color) ->
                val heightFactor = amount / max
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text("R$ 200", fontSize = 10.sp)
                    Box(
                        Modifier
                            .width(20.dp)
                            .fillMaxHeight(fraction = heightFactor)
                            .background(color)
                    )
                }
            }
        }
    }
}