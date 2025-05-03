package com.eliasjuniornino.budgetplanner.screens.home_screen

import com.eliasjuniornino.budgetplanner.repositories.ExpensesRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import moe.tlaster.precompose.viewmodel.ViewModel
import moe.tlaster.precompose.viewmodel.viewModelScope

class HomeScreenViewModel(
    private val expensesRepository: ExpensesRepository
) : ViewModel() {
    private val _uiState = MutableStateFlow(HomeScreenUIState())
    val uiState = _uiState.asStateFlow()

    private val _expensesTotal = MutableStateFlow<Double>(0.0)
    val expensesTotal = _expensesTotal.asStateFlow()

    init {
        fetchInitialData()
    }

    fun fetchInitialData() {
        _uiState.update { it.apply { isLoading = true } }
        try {
            viewModelScope.launch {
                _expensesTotal.value = expensesRepository.getExpensesTotal()
            }
        } catch (_: Exception) {

        } finally {
            _uiState.update { it.apply { isLoading = false } }
        }
    }
}