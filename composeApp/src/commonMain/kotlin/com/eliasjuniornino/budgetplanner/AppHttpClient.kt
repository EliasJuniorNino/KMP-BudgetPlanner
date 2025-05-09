package com.eliasjuniornino.budgetplanner

import io.ktor.client.*
import io.ktor.client.plugins.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.plugins.logging.*
import io.ktor.client.request.header
import io.ktor.http.*
import io.ktor.serialization.kotlinx.json.*

object AppHttpClient {
    private var client: HttpClient? = null
    private var token: String? = null

    fun setToken(newTokenValue: String) {
        token = newTokenValue
        client = createClient()
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
                url("http://192.168.1.16:8080/")
            }
            install(ContentNegotiation) {
                json()
            }
            install(Logging) {
                level = LogLevel.ALL
            }
            install(DefaultRequest) {
                token?.let {
                    headers.append("Authorization", "Bearer $it")
                }
            }
        }
    }
}
