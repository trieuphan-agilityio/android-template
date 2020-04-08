package com.example.core.data.remote.response

import com.example.core.data.entities.User
import com.google.gson.annotations.SerializedName

class UserDataResponse(
    responseCode: Int,
    message: String
) : BaseResponse(responseCode, message) {

    @SerializedName("userData")
    var user: User? = null
}