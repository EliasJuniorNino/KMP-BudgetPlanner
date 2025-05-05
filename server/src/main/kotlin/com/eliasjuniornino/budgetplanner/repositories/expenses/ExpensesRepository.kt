package com.eliasjuniornino.budgetplanner.repositories.expenses

import com.eliasjuniornino.budgetplanner.models.ExpenseModel

class ExpensesRepository() : IExpensesRepository {
    override suspend fun list(userId: Int): List<ExpenseModel> {
        TODO("Not yet implemented")
    }

    override suspend fun store(userId: Int, data: ExpenseModel): ExpenseModel {
        TODO("Not yet implemented")
    }

    override suspend fun get(userId: Int, id: Int): ExpenseModel? {
        TODO("Not yet implemented")
    }

    override suspend fun update(userId: Int, data: ExpenseModel): ExpenseModel {
        TODO("Not yet implemented")
    }

    override suspend fun delete(userId: Int, id: Int): Boolean {
        TODO("Not yet implemented")
    }

}