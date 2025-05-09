package com.eliasjuniornino.budgetplanner.repositories

import com.eliasjuniornino.budgetplanner.dto.incomes.CreateIncomesDTO
import com.eliasjuniornino.budgetplanner.dto.incomes.IncomeDTO
import com.eliasjuniornino.budgetplanner.dto.incomes.UpdateIncomesDTO
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.http.ContentType
import io.ktor.http.HttpStatusCode
import io.ktor.http.contentType

class IncomeRepositoryImpl(private val client: HttpClient) : IncomeRepository {
    override suspend fun list(): List<IncomeDTO> {
        val response = client.get("api/incomes")
        return response.body()
    }

    override suspend fun view(id: Int): IncomeDTO {
        val response = client.get("api/incomes/$id")
        return response.body()
    }

    override suspend fun create(data: CreateIncomesDTO): IncomeDTO {
        val response = client.post("api/incomes") {
            contentType(ContentType.Application.Json)
            setBody(data)
        }
        return response.body()
    }

    override suspend fun update(data: UpdateIncomesDTO): IncomeDTO {
        val response = client.post("api/incomes") {
            contentType(ContentType.Application.Json)
            setBody(data)
        }
        return response.body()
    }

    override suspend fun delete(id: Int): Boolean {
        val response = client.get("api/incomes")
        return response.status == HttpStatusCode.OK
    }

}