package com.eliasjuniornino.budgetplanner

import io.ktor.client.*
import io.ktor.client.plugins.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.plugins.logging.*
import io.ktor.serialization.kotlinx.json.*

object AppHttpClient {
    private var client: HttpClient? = null
    private var token: String? = null
    private var accountId: Int? = null

    fun setToken(newTokenValue: String) {
        token = newTokenValue
    }

    fun setAccountId(newAccountId: Int) {
        accountId = newAccountId
    }

    fun getClient(): HttpClient {
        if (client == null) {
            client = createClient()
        }
        return client!!
    }

    private fun createClient(): HttpClient {
        return HttpClient {
            defaultRequest {
                url("http://192.168.1.12:8080/")
            }
            install(ContentNegotiation) {
                json()
            }
            install(Logging) {
                level = LogLevel.ALL
            }
            install(DefaultRequest) {
                token?.let { headers.append("Authorization", "Bearer $it") }
                accountId?.let { headers.append("account_id", "$accountId") }
            }
        }
    }
}
