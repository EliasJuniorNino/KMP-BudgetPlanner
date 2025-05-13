package com.eliasjuniornino.budgetplanner.screens.home_screen.tabs.home_tab.components

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.eliasjuniornino.budgetplanner.dto.dashboard.WalletResumeDTO
import com.eliasjuniornino.budgetplanner.extensions.toMoneyStr

@Composable
fun Header(
    walletResume: WalletResumeDTO,
) {
    Card {
        Column(Modifier.padding(12.dp)) {
            Text("Maio 2025", fontSize = 18.sp, fontWeight = FontWeight.Bold)
        }
    }

    Spacer(Modifier.height(16.dp))

    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text("Despesas", color = Color.Gray)
            Text(walletResume.totalExpenses.toMoneyStr(), color = Color.Red, fontWeight = FontWeight.Bold)
        }
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text("Receitas", color = Color.Gray)
            Text(
                walletResume.totalIncomes.toMoneyStr(),
                color = Color(0xFF00C853),
                fontWeight = FontWeight.Bold
            )
        }
    }

    Spacer(Modifier.height(12.dp))

    LinearProgressIndicator(
        progress = { 0.55f },
        modifier = Modifier
            .fillMaxWidth()
            .height(20.dp)
            .clip(RoundedCornerShape(10.dp)),
        color = Color.Red
    )

    Text(
        "97,51%", color = Color.White, modifier = Modifier
            .offset(y = (-20).dp)
    )

    Spacer(Modifier.height(24.dp))

    Text("Saldo previsto", fontWeight = FontWeight.SemiBold)
    Text("R$ 212,90", color = Color(0xFF00C853), fontWeight = FontWeight.Bold)

    Spacer(Modifier.height(16.dp))
}