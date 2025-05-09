package com.eliasjuniornino.budgetplanner.repositories

import com.eliasjuniornino.budgetplanner.dto.income_categories.CreateIncomeCategoryDTO
import com.eliasjuniornino.budgetplanner.dto.income_categories.IncomeCategoryDTO
import com.eliasjuniornino.budgetplanner.dto.income_categories.UpdateIncomeCategoryDTO
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.post
import io.ktor.client.request.put
import io.ktor.client.request.setBody
import io.ktor.http.ContentType
import io.ktor.http.HttpStatusCode
import io.ktor.http.contentType

class IncomeCategoryRepositoryImpl(private val client: HttpClient) : IncomeCategoryRepository {
    override suspend fun list(): List<IncomeCategoryDTO> {
        val response = client.get("api/income-categories")
        return response.body()
    }

    override suspend fun listSub(id: Int): List<IncomeCategoryDTO> {
        val response = client.get("api/income-sub-categories")
        return response.body()
    }

    override suspend fun view(id: Int): IncomeCategoryDTO {
        val response = client.get("api/income-categories/$id")
        return response.body()
    }

    override suspend fun create(data: CreateIncomeCategoryDTO): IncomeCategoryDTO {
        val response = client.post("api/income-categories") {
            contentType(ContentType.Application.Json)
            setBody(data)
        }
        return response.body()
    }

    override suspend fun update(data: UpdateIncomeCategoryDTO): IncomeCategoryDTO {
        val response = client.put("api/income-categories") {
            contentType(ContentType.Application.Json)
            setBody(data)
        }
        return response.body()
    }

    override suspend fun delete(id: Int): Boolean {
        val response = client.get("api/income-categories")
        return response.status == HttpStatusCode.OK
    }
}