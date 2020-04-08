package com.example.core.data.repository

import com.example.core.data.remote.common.Result
import com.example.core.data.remote.request.BaseRequest
import com.example.core.data.remote.response.UserDataResponse
import io.reactivex.Single

interface UserRepository {
    fun login(
        body: BaseRequest
    ): Single<Result<UserDataResponse>>
}