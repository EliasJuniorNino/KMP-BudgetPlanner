package com.eliasjuniornino.budgetplanner.screens.home_screen

import com.eliasjuniornino.budgetplanner.dto.dashboard.ExpenseByCategoryDTO
import com.eliasjuniornino.budgetplanner.dto.dashboard.WalletResumeDTO
import com.eliasjuniornino.budgetplanner.repositories.DashboardRepositoryImpl
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import moe.tlaster.precompose.viewmodel.ViewModel
import moe.tlaster.precompose.viewmodel.viewModelScope

class HomeScreenViewModel(
    private val dashboardRepository: DashboardRepositoryImpl
) : ViewModel() {
    private val _uiState = MutableStateFlow(HomeScreenUIState())
    val uiState = _uiState.asStateFlow()

    private val _walletResume = MutableStateFlow<WalletResumeDTO?>(null)
    val walletResume = _walletResume.asStateFlow()

    private val _expensesByCategory = MutableStateFlow<List<ExpenseByCategoryDTO>?>(null)
    val expensesByCategory = _expensesByCategory.asStateFlow()

    init {
        fetchInitialData()
    }

    fun fetchInitialData() {
        _uiState.update { it.apply { isLoading = true } }
        try {
            viewModelScope.launch {
                _walletResume.value = dashboardRepository.getWalletResume()
            }
        } catch (_: Exception) {

        } finally {
            _uiState.update { it.apply { isLoading = false } }
        }
    }
}