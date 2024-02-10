package com.example.core.networking.data


data class APIResponse<out T>(
    val status: Status?,
    val data: T? = null,
    val errorCode: Int? = null,
    val errorMessage: String? = null

) {
    enum class Status {
        SUCCESS, ERROR, LOADING, IDLE
    }

    companion object {
        fun <T> success(data: T): APIResponse<T> {
            return APIResponse(Status.SUCCESS, data)
        }

        fun <T> loading(): APIResponse<T> {
            return APIResponse(Status.LOADING)
        }

        fun <T> idle(): APIResponse<T> {
            return APIResponse(Status.IDLE)
        }

        fun <T> error(
            errorMessage: String, errorCode: Int? = null
        ): APIResponse<T> {
            return APIResponse(
                Status.ERROR, errorMessage = errorMessage, errorCode = errorCode
            )
        }
    }


}