package com.example.core.networking.api

import io.ktor.client.HttpClient

interface HttpClientAPI {
    fun getHttpClient(): HttpClient
}