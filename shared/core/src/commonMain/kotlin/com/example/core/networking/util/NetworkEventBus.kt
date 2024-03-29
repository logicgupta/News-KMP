package com.example.core.networking.util

import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow

class NetworkEventBus private constructor() {

    companion object {
        val INSTANCE by lazy {
            NetworkEventBus()
        }
    }

    private val _events = MutableSharedFlow<NetworkApiEvent>()
    val events: SharedFlow<NetworkApiEvent> = _events

    suspend fun invokeEvent(event: NetworkApiEvent) = _events.emit(event)
}

enum class NetworkApiEvent {
    REFRESH_TOKEN_EXPIRED
}