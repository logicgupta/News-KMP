package com.example.core.networking.api

import io.ktor.client.engine.HttpClientEngine

expect class HttpEngineProvider() {
    fun clientEngine(shouldEnableLogging: Boolean): HttpClientEngine
}