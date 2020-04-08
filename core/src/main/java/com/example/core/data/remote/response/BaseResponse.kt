package com.example.core.data.remote.response

open class BaseResponse(var responseCode: Int, var message: String) {

    companion object {
        const val SUCCESS = 0
        const val MESSAGE = "SUCCESS"
    }

    /**
     * Check status of the request is failed or success
     *
     * @return true if the request is failed.
     */
    fun hasError(): Boolean = responseCode != SUCCESS
}