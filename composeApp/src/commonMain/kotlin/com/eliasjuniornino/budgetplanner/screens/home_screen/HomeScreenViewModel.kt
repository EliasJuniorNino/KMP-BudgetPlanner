package com.eliasjuniornino.budgetplanner.screens.home_screen

import com.eliasjuniornino.budgetplanner.AppHttpClient
import com.eliasjuniornino.budgetplanner.dto.accounts.AccountDTO
import com.eliasjuniornino.budgetplanner.dto.auth.AuthLoginDTO
import com.eliasjuniornino.budgetplanner.dto.auth.AuthSignupDTO
import com.eliasjuniornino.budgetplanner.dto.dashboard.AIResumeDTO
import com.eliasjuniornino.budgetplanner.dto.dashboard.ExpenseByCategoryDTO
import com.eliasjuniornino.budgetplanner.dto.dashboard.WalletResumeDTO
import com.eliasjuniornino.budgetplanner.dto.expense_categories.ExpenseCategoryDTO
import com.eliasjuniornino.budgetplanner.dto.expenses.ExpenseDTO
import com.eliasjuniornino.budgetplanner.dto.income_categories.IncomeCategoryDTO
import com.eliasjuniornino.budgetplanner.dto.incomes.IncomeDTO
import com.eliasjuniornino.budgetplanner.dto.user.UserDTO
import com.eliasjuniornino.budgetplanner.repositories.DashboardRepository
import com.eliasjuniornino.budgetplanner.repositories.ExpenseCategoryRepository
import com.eliasjuniornino.budgetplanner.repositories.ExpenseRepository
import com.eliasjuniornino.budgetplanner.repositories.IncomeCategoryRepository
import com.eliasjuniornino.budgetplanner.repositories.IncomeRepository
import com.eliasjuniornino.budgetplanner.repositories.UserRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import moe.tlaster.precompose.viewmodel.ViewModel
import moe.tlaster.precompose.viewmodel.viewModelScope

class HomeScreenViewModel(
    private val dashboardRepository: DashboardRepository,
    private val incomesRepository: IncomeRepository,
    private val expensesRepository: ExpenseRepository,
    private val incomeCategoryRepository: IncomeCategoryRepository,
    private val expenseCategoryRepository: ExpenseCategoryRepository,
    private val userRepository: UserRepository,
) : ViewModel() {
    private val _uiState = MutableStateFlow(HomeScreenUIState())
    val uiState = _uiState.asStateFlow()

    private val _aiResume = MutableStateFlow<AIResumeDTO?>(null)
    val aiResume = _aiResume.asStateFlow()

    private val _walletResume = MutableStateFlow<WalletResumeDTO?>(null)
    val walletResume = _walletResume.asStateFlow()

    private val _expensesByCategory = MutableStateFlow<List<ExpenseByCategoryDTO>?>(null)
    val expensesByCategory = _expensesByCategory.asStateFlow()

    private val _incomes = MutableStateFlow<List<IncomeDTO>?>(null)
    val incomes = _incomes.asStateFlow()

    private val _incomeCategories = MutableStateFlow<List<IncomeCategoryDTO>?>(null)
    val incomeCategories = _incomeCategories.asStateFlow()

    private val _expenses = MutableStateFlow<List<ExpenseDTO>?>(null)
    val expenses = _expenses.asStateFlow()

    private val _expenseCategories = MutableStateFlow<List<ExpenseCategoryDTO>?>(null)
    val expenseCategories = _expenseCategories.asStateFlow()

    private val _user = MutableStateFlow<UserDTO?>(null)
    val user = _user.asStateFlow()

    private val _accounts = MutableStateFlow<List<AccountDTO>?>(null)
    val accounts = _accounts.asStateFlow()

    private val _selectedAccount = MutableStateFlow<AccountDTO?>(null)
    val selectedAccount = _selectedAccount.asStateFlow()

    init {
        doLogin("elias@example.com", "senha123")
    }

    fun doLogin(email: String, password: String) {
        _uiState.update { it.apply { isLoading = true } }
        try {
            viewModelScope.launch {
                val loginBody = AuthLoginDTO(
                    email = email,
                    password = password
                )
                val loginReturnDTO = userRepository.doLogin(loginBody)
                AppHttpClient.setToken(loginReturnDTO.token)
                _user.value = loginReturnDTO.user
                _accounts.value = loginReturnDTO.accounts
                if (loginReturnDTO.accounts.isNotEmpty()) {
                    val selectedAccount = loginReturnDTO.accounts.first()
                    _selectedAccount.value = selectedAccount
                    AppHttpClient.setAccountId(selectedAccount.id)
                }
            }
        } catch (_: Exception) {

        } finally {
            _uiState.update { it.apply { isLoading = false } }
        }
    }

    fun doSignup(name: String, email: String, password: String) {
        _uiState.update { it.apply { isLoading = true } }
        try {
            viewModelScope.launch {
                val signupBody = AuthSignupDTO(
                    name = name,
                    email = email,
                    password = password
                )
                userRepository.doSignup(signupBody)
                _user.value = userRepository.getUser()
            }
        } catch (_: Exception) {

        } finally {
            _uiState.update { it.apply { isLoading = false } }
        }
    }

    fun fetchHomeData() {
        _uiState.update { it.apply { isLoading = true } }
        try {
            viewModelScope.launch {
                _aiResume.value = dashboardRepository.getAIResume()
            }
            viewModelScope.launch {
                _walletResume.value = dashboardRepository.getWalletResume()
            }
            viewModelScope.launch {
                _expensesByCategory.value = dashboardRepository.getExpensesByCategory()
            }
        } catch (_: Exception) {

        } finally {
            _uiState.update { it.apply { isLoading = false } }
        }
    }

    fun fetchIncomesData() {
        _uiState.update { it.apply { isLoading = true } }
        try {
            viewModelScope.launch {
                _incomes.value = incomesRepository.list()
            }
            viewModelScope.launch {
                _incomeCategories.value = incomeCategoryRepository.list()
            }
        } catch (_: Exception) {

        } finally {
            _uiState.update { it.apply { isLoading = false } }
        }
    }

    fun fetchExpensesData() {
        _uiState.update { it.apply { isLoading = true } }
        try {
            viewModelScope.launch {
                _expenses.value = expensesRepository.list()
            }
            viewModelScope.launch {
                _expenseCategories.value = expenseCategoryRepository.list()
            }
        } catch (_: Exception) {

        } finally {
            _uiState.update { it.apply { isLoading = false } }
        }
    }
}