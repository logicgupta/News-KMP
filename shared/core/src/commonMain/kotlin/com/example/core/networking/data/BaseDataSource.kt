package com.example.core.networking.data

import com.example.core.networking.util.NetworkApiEvent
import com.example.core.networking.util.NetworkConstants.NetworkErrorCodes
import com.example.core.networking.util.NetworkConstants.NetworkErrorMessages
import com.example.core.networking.util.NetworkEventBus
import io.ktor.client.call.body
import io.ktor.client.network.sockets.SocketTimeoutException
import io.ktor.client.plugins.ClientRequestException
import io.ktor.client.plugins.ServerResponseException
import io.ktor.client.statement.HttpResponse
import io.ktor.http.HttpStatusCode
import io.ktor.serialization.JsonConvertException
import io.ktor.util.network.UnresolvedAddressException
import io.ktor.utils.io.errors.IOException
import kotlinx.coroutines.CancellationException
import kotlinx.serialization.SerializationException

abstract class BaseDataSource {

    protected suspend inline fun <reified T> getResult(call: () -> HttpResponse): APIResponse<T> {

        val result: HttpResponse?
        return try {
            result = call()
            if (result.status == HttpStatusCode.OK) {
                val data: T = result.body()
                APIResponse.success(data)
            } else {
                APIResponse.error(
                    errorMessage = NetworkErrorMessages.SOME_ERROR_OCCURRED,
                    errorCode = result.status.value
                )
            }
        } catch (e: ClientRequestException) {
            return when (val statusCode = e.response.status.value) {
                NetworkErrorCodes.ACCESS_TOKEN_EXPIRED -> {
                    APIResponse.error(
                        errorMessage = NetworkErrorMessages.ACCESS_TOKEN_EXPIRED,
                        errorCode = statusCode
                    )
                }

                NetworkErrorCodes.REFRESH_TOKEN_EXPIRED -> {
                    // Not Valid For whom we don't have refresh token mechanism
                    NetworkEventBus.INSTANCE.invokeEvent(NetworkApiEvent.REFRESH_TOKEN_EXPIRED)
                    APIResponse.error(
                        errorMessage = NetworkErrorMessages.PLEASE_LOGIN_AGAIN,
                        errorCode = statusCode
                    )
                }

                else -> {
                    APIResponse.error(
                        errorMessage = e.message,
                        errorCode = NetworkErrorCodes.UNKNOWN_ERROR_OCCURRED
                    )
                }
            }
        } catch (e: ServerResponseException) {
            val statusCode = e.response.status.value
            APIResponse.error(
                errorMessage = NetworkErrorMessages.APP_UNDER_MAINTENANCE, errorCode = statusCode
            )
        } catch (e: IOException) {
            APIResponse.error(
                errorMessage = NetworkErrorMessages.PLEASE_CHECK_YOUR_INTERNET_CONNECTION,
                errorCode = NetworkErrorCodes.INTERNET_NOT_WORKING
            )
        } catch (e: UnresolvedAddressException) {
            APIResponse.error(
                errorMessage = NetworkErrorMessages.PLEASE_CHECK_YOUR_INTERNET_CONNECTION,
                errorCode = NetworkErrorCodes.INTERNET_NOT_WORKING
            )
        } catch (e: SocketTimeoutException) {
            APIResponse.error(
                errorMessage = NetworkErrorMessages.PLEASE_CHECK_YOUR_INTERNET_CONNECTION,
                errorCode = NetworkErrorCodes.INTERNET_NOT_WORKING
            )
        } catch (e: SerializationException) {
            e
            APIResponse.error(
                errorMessage = NetworkErrorMessages.DATA_SERIALIZATION_ERROR,
                errorCode = NetworkErrorCodes.DATA_SERIALIZATION_ERROR,
            )
        } catch (e: JsonConvertException) {
            e
            APIResponse.error(
                errorMessage = NetworkErrorMessages.DATA_SERIALIZATION_ERROR,
                errorCode = NetworkErrorCodes.DATA_SERIALIZATION_ERROR
            )
        } catch (e: CancellationException) {
            APIResponse.error(
                errorMessage = "",  //This is a special case in which we don't want to show any error , Since User has himself Terminated connection
                errorCode = NetworkErrorCodes.NETWORK_CALL_CANCELLED
            )
        } catch (e: Exception) {
            APIResponse.error(
                errorMessage = e.message ?: NetworkErrorMessages.SOME_ERROR_OCCURRED,
                errorCode = NetworkErrorCodes.UNKNOWN_ERROR_OCCURRED
            )
        }


    }
}