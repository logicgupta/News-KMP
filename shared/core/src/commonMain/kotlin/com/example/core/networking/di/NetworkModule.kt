package com.example.core.networking.di

import com.example.core.CoreBuildKonfig
import com.example.core.networking.Impl.HttpClientImpl
import com.example.core.networking.api.HttpClientAPI
import io.ktor.client.HttpClient
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.json.Json
import org.koin.dsl.module

@OptIn(ExperimentalSerializationApi::class)
val networkModule = module {

    single<HttpClientAPI> {
        HttpClientImpl(
            shouldEnableLogging = CoreBuildKonfig.IS_DEBUG, json = get(), loggerApi = get()
        )
    }

    single {
        Json {
            ignoreUnknownKeys = true
            prettyPrint = false
            isLenient = true
            useAlternativeNames = true
            encodeDefaults = true
            explicitNulls = false
        }
    }

    single {
        provideHttpClient(get())
    }
}

private fun provideHttpClient(httpClientApi: HttpClientAPI): HttpClient {
    return httpClientApi.getHttpClient()
}