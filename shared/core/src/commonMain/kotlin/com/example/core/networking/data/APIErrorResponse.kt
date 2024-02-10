package com.example.core.networking.data

data class APIErrorResponse(
    val errorMessage: String
) : Exception(errorMessage)