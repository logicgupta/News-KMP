package com.example.core.networking.util

import com.example.core.networking.data.APIResponse


suspend fun <T> APIResponse<T>.onSuccess(lambda: suspend (data: T) -> Unit): APIResponse<T> {
    if (status == APIResponse.Status.SUCCESS) {
        lambda.invoke(data!!)
    }
    return this
}

suspend fun <T> APIResponse<T>.onError(lambda: suspend (errorMessage: String, errorCode: Int?) -> Unit): APIResponse<T> {
    if (status == APIResponse.Status.ERROR) {
        lambda.invoke(errorMessage.orEmpty(), errorCode)
    }
    return this
}

suspend fun <T> APIResponse<T>.onLoading(lambda: suspend () -> Unit): APIResponse<T> {
    if (status == APIResponse.Status.LOADING) {
        lambda.invoke()
    }
    return this
}

suspend fun <T> APIResponse<T>.isLoading() = status == APIResponse.Status.LOADING
suspend fun <T> APIResponse<T>.isSuccess() = status == APIResponse.Status.SUCCESS
suspend fun <T> APIResponse<T>.isError() = status == APIResponse.Status.ERROR