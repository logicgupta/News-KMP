package com.example.core.networking.Impl

import com.example.core.CoreBuildKonfig
import com.example.core.logger.api.LoggerApi
import com.example.core.networking.api.HttpClientAPI
import com.example.core.networking.api.HttpEngineProvider
import com.example.core.networking.util.NetworkConstants
import io.ktor.client.HttpClient
import io.ktor.client.plugins.HttpTimeout
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.defaultRequest
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logger
import io.ktor.client.plugins.logging.Logging
import io.ktor.client.plugins.observer.ResponseObserver
import io.ktor.client.request.header
import io.ktor.client.statement.bodyAsText
import io.ktor.http.ContentType
import io.ktor.http.URLProtocol
import io.ktor.http.contentType
import io.ktor.serialization.kotlinx.json.json
import kotlinx.coroutines.runBlocking
import kotlinx.serialization.json.Json

class HttpClientImpl(
    private val shouldEnableLogging: Boolean,
    private val json: Json,
    private val loggerApi: LoggerApi,
) : HttpClientAPI {

    private val httpEngineProvider by lazy {
        HttpEngineProvider()
    }


    override fun getHttpClient(): HttpClient {
        return HttpClient(httpEngineProvider.clientEngine(shouldEnableLogging)) {

            expectSuccess = true

            defaultRequest {

                host = CoreBuildKonfig.BASE_URL

                url {
                    protocol = URLProtocol.HTTPS
                }
                contentType(ContentType.Application.Json)
                // Access Token
                val accessToken: String? = runBlocking {
                    "UU" ?: null
                }
                if (accessToken.isNullOrBlank().not()) {
                    // Flexibility to add multiple headers
                    header(
                        key = NetworkConstants.AuthHeader.AUTH_HEADER_KEY,
                        value = NetworkConstants.AuthHeader.BEARER.plus(accessToken)
                    )
                }
                //   header(key = "Platform", getPlatform())
            }

            //Timeout
            install(HttpTimeout) {
                requestTimeoutMillis = NetworkConstants.NetworkApiConfig.REQUEST_TIMEOUT_MILLIS
                socketTimeoutMillis = NetworkConstants.NetworkApiConfig.SOCKET_TIMEOUT_MILLIS
                connectTimeoutMillis = NetworkConstants.NetworkApiConfig.CONNECT_TIMEOUT_MILLIS
            }

            //Logging
            if (shouldEnableLogging) {
                install(Logging) {
                    logger = object : Logger {
                        override fun log(message: String) {
                            loggerApi.logDWithTag(
                                tag = NetworkConstants.NetworkApiConfig.DEFAULT_LOG_TAG,
                                message = message
                            )
                        }
                    }
                    level = LogLevel.ALL
                }
            }


            // Response Observer
            if (shouldEnableLogging) {
                install(ResponseObserver) {
                    onResponse {
                        val message = it.bodyAsText()
                        loggerApi.logDWithTag(
                            tag = NetworkConstants.NetworkApiConfig.DEFAULT_LOG_TAG,
                            message = message
                        )
                    }
                }
            }

            //Serialization
            install(ContentNegotiation) {
                json(json)
            }


        }
    }
}