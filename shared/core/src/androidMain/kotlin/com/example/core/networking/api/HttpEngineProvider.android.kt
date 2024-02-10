package com.example.core.networking.api

import io.ktor.client.engine.HttpClientEngine
import io.ktor.client.engine.okhttp.OkHttp

actual class HttpEngineProvider actual constructor() {
    actual fun clientEngine(shouldEnableLogging: Boolean): HttpClientEngine {
        return OkHttp.create()
    }
}