package com.eliasjuniornino.budgetplanner.repositories

import com.eliasjuniornino.budgetplanner.AppHttpClient
import com.eliasjuniornino.budgetplanner.dto.accounts.AccountDTO
import com.eliasjuniornino.budgetplanner.dto.auth.AuthLoginDTO
import com.eliasjuniornino.budgetplanner.dto.auth.AuthLoginReturnDTO
import com.eliasjuniornino.budgetplanner.dto.auth.AuthSignupDTO
import com.eliasjuniornino.budgetplanner.dto.auth.AuthSignupReturnDTO
import com.eliasjuniornino.budgetplanner.dto.user.UserDTO
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.client.statement.HttpResponse
import io.ktor.http.ContentType
import io.ktor.http.contentType

class UserRepositoryImpl(private val client: HttpClient) : UserRepository {
    override suspend fun doLogin(data: AuthLoginDTO) : AuthLoginReturnDTO {
        val response: HttpResponse = client.post("auth/login") {
            contentType(ContentType.Application.Json)
            setBody(data)
        }
        return response.body()
    }

    override suspend fun doSignup(data: AuthSignupDTO) : AuthSignupReturnDTO {
        val response: HttpResponse = client.post("auth/signup") {
            contentType(ContentType.Application.Json)
            setBody(data)
        }
        return response.body()
    }

    override suspend fun getUser(): UserDTO {
        val response = client.get("api/user")
        return response.body()
    }

    override suspend fun getAccounts(): List<AccountDTO> {
        val response = client.get("api/accounts")
        return response.body()
    }
}