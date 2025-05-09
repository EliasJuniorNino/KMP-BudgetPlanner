package com.eliasjuniornino.budgetplanner.repositories

import com.eliasjuniornino.budgetplanner.dto.expenses.CreateExpenseDTO
import com.eliasjuniornino.budgetplanner.dto.expenses.ExpenseDTO
import com.eliasjuniornino.budgetplanner.dto.expenses.UpdateExpenseDTO
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.delete
import io.ktor.client.request.get
import io.ktor.client.request.post
import io.ktor.client.request.put
import io.ktor.client.request.setBody
import io.ktor.http.ContentType
import io.ktor.http.HttpStatusCode
import io.ktor.http.contentType

class ExpenseRepositoryImpl(private val client: HttpClient) : ExpenseRepository {
    override suspend fun list(): List<ExpenseDTO> {
        val response = client.get("api/expenses")
        return response.body()
    }

    override suspend fun view(id: Int): ExpenseDTO {
        val response = client.get("api/expenses/$id")
        return response.body()
    }

    override suspend fun create(data: CreateExpenseDTO): ExpenseDTO {
        val response = client.post("api/expenses") {
            contentType(ContentType.Application.Json)
            setBody(data)
        }
        return response.body()
    }

    override suspend fun update(data: UpdateExpenseDTO): ExpenseDTO {
        val response = client.put("api/expenses") {
            contentType(ContentType.Application.Json)
            setBody(data)
        }
        return response.body()
    }

    override suspend fun delete(id: Int): Boolean {
        val response = client.delete("api/expenses/$id")
        return response.status == HttpStatusCode.OK
    }
}