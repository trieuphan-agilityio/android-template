package com.example.core.data.remote.common

/**
 * This class handle errors and notify to View for handle showing error in app.
 */
data class Error(
    val code: Int,
    val message: String? = null
) {
    companion object {

        // FIXME: Define list of error in app
        const val unknownErrorCode = -1
    }
}

/**
 * The error code from server side.
 */
fun Error.isServerError() = code == 500 || code == 503 || code == 504

/**
 * The error is client errors (Bad request or resource not found
 */
fun Error.isClientError() = code == 400 || code == 404