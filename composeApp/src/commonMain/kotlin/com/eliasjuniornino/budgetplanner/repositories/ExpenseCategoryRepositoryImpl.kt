package com.eliasjuniornino.budgetplanner.repositories

import com.eliasjuniornino.budgetplanner.dto.expense_categories.CreateExpenseCategoryDTO
import com.eliasjuniornino.budgetplanner.dto.expense_categories.ExpenseCategoryDTO
import com.eliasjuniornino.budgetplanner.dto.expense_categories.UpdateExpenseCategoryDTO
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

class ExpenseCategoryRepositoryImpl(private val client: HttpClient): ExpenseCategoryRepository {
    override suspend fun list(): List<ExpenseCategoryDTO> {
        val response = client.get("api/expense-categories")
        return response.body()
    }

    override suspend fun listSub(id: Int): List<ExpenseCategoryDTO> {
        val response = client.get("api/expense-sub-categories")
        return response.body()
    }

    override suspend fun view(id: Int): ExpenseCategoryDTO {
        val response = client.get("api/expense-categories/$id")
        return response.body()
    }

    override suspend fun create(data: CreateExpenseCategoryDTO): ExpenseCategoryDTO {
        val response = client.post("api/expense-categories") {
            contentType(ContentType.Application.Json)
            setBody(data)
        }
        return response.body()
    }

    override suspend fun update(data: UpdateExpenseCategoryDTO): ExpenseCategoryDTO {
        val response = client.put("api/expense-categories") {
            contentType(ContentType.Application.Json)
            setBody(data)
        }
        return response.body()
    }

    override suspend fun delete(id: Int): Boolean {
        val response = client.delete("api/expense-categories/$id")
        return response.status == HttpStatusCode.OK
    }
}