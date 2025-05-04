package com.eliasjuniornino.budgetplanner.services.user

import com.eliasjuniornino.budgetplanner.dto.dashboard.ResumeWalletReturnDTO

interface IUserService {
    fun getExpenses(): ResumeWalletReturnDTO
}