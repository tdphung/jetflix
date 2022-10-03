package com.littlewind.android.base.exception

sealed class Failure {
    /** error is returned from server */
    class ServerError(val code: Int = -1, val message: String = "", val errorBody: String? = null) : Failure()

    /** error happens in client */
    class ClientError(val code: Int = -2, val message: String = "") : Failure()

    /** error happens while reading the response */
    class ResponseError(val code: Int = 0, val message: String = "") : Failure()

    class Error(val message: String = "Không xác định!") : Failure()
    class ExceptionError(val exception: Exception) : Failure()
    class NetworkConnection : Failure()

    companion object {
        const val TYPE_OK = -1
        const val TYPE_NOT_FOUND = 0
        const val TYPE_NO_UNREACH = 1
        const val TYPE_UNAUTHORIZE = 2
        const val TYPE_ACTIVATED = 3
        const val TYPE_ERROR_REQUEST = 4
        const val TYPE_ACTIVE = 5
        const val TYPE_CODE_NETWORK = 12321
        const val TYPE_UNKNOW = 123456

        fun getTypeError(failure: Failure): Int {
            if (failure is ServerError) {
                if (failure.message.contains("error") && (failure.message.contains("Not Found") || failure.message.contains(
                        "doesn't exist"
                    ))
                ) {
                    return TYPE_NOT_FOUND
                } else if (failure.message.contains("Unable to resolve host")) {
                    return TYPE_NO_UNREACH
                } else if (failure.message.contains("activated")) {
                    return TYPE_ACTIVATED
                } else if (failure.code == 422) {
                    return TYPE_ERROR_REQUEST
                } else if (failure.code == TYPE_CODE_NETWORK) {
                    return TYPE_CODE_NETWORK
                } else if (failure.code == TYPE_UNKNOW) {
                    return TYPE_UNKNOW
                }
            }
            return TYPE_UNAUTHORIZE
        }

        fun getErrorMsg(failure: Failure): String {
            return when (failure) {
                is ServerError -> {
                    "ServerError: code:${failure.code} - message: ${failure.message}"
                }
                is ClientError -> {
                    "ClientError: code:${failure.code} - message: ${failure.message}"
                }
                is ResponseError -> {
                    "ResponseError: code:${failure.code} - message: ${failure.message}"
                }
                is Error -> {
                    "Unknownnn Error"
                }
                is ExceptionError -> {
                    "ExceptionError: ${failure.exception.stackTrace}"
                }
                is NetworkConnection -> {
                    "NetworkConnectionnn"
                }
                else -> {
                    "Unknownnn Failureee"
                }
            }
        }
    }
}