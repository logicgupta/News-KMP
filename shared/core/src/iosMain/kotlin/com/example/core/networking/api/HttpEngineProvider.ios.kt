package com.example.core.networking.api

import io.ktor.client.engine.HttpClientEngine
import io.ktor.client.engine.darwin.Darwin

actual class HttpEngineProvider actual constructor() {
    actual fun clientEngine(shouldEnableLogging: Boolean): HttpClientEngine {
        return Darwin.create()
    }
}