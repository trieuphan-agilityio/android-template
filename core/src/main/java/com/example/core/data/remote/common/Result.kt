package com.example.core.data.remote.common

import com.example.core.data.common.ApiEmptyResponse
import com.example.core.data.common.ApiErrorResponse
import com.example.core.data.common.ApiResponse
import com.example.core.data.common.ApiSuccessResponse
import com.example.core.data.remote.response.BaseResponse

/**
 * A generic class that holds a value with its loading status.
 * @param <T>
</T> */
data class Result<out T>(val status: Status, val data: T?, val error: Error? = null) {

    companion object {
        fun <T> success(data: T?): Result<T> {
            return Result(Status.SUCCESS, data)
        }

        fun <T> error(code: Int, message: String, data: T? = null): Result<T> {
            return Result(Status.ERROR, data, Error(code, message))
        }

        fun <T> loading(data: T?): Result<T> {
            return Result(Status.LOADING, data)
        }

        /**
         * Build result from ApiResponse.
         */
        fun <T: BaseResponse> fromResponse(
            response: ApiResponse<T>,
            dataHandler: ((data: T) -> Unit)? = null
        ): Result<T> {

            return when (response) {
                is ApiSuccessResponse -> {
                    val data = response.body

                    // Has in app error
                    if (data.hasError()) {
                        error(data.responseCode, data.message, data)
                    } else { // Successful
                        dataHandler?.invoke(data)
                        success(data)
                    }
                }

                is ApiEmptyResponse -> {
                    success(null)
                }

                is ApiErrorResponse -> {
                    error(response.responseCode, response.errorMessage)
                }
            }
        }

        /**
         * Build result from an exception.
         */
        fun <T> fromException(e: Throwable): Result<T> {
            return error(Error.unknownErrorCode, e.message.toString())
        }
    }
}

/**
 * Check the response result is success or not
 */
fun <T> Result<T>.isSuccess(): Boolean {
    return this.status == Status.SUCCESS
}

/**
 * Transform Result of the object to Result of another object
 *
 * @param transform The transform function
 *
 * @return The Result of transformed object
 */
inline fun <T, R> Result<T>.map(transform: (T) -> R): Result<R> {
    return Result(this.status, this.data?.let { transform(it) }, this.error)
}