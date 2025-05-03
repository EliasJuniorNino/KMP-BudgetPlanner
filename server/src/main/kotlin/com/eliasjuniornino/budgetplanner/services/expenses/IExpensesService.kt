package com.eliasjuniornino.budgetplanner.services.expenses

import com.eliasjuniornino.budgetplanner.dto.dashboard.ResumeWalletReturnDTO

interface IAuthService {
  fun getExpenses(): ResumeWalletReturnDTO
}