package com.example.core.data.remote

import com.example.core.data.common.ApiResponse
import com.example.core.data.remote.request.BaseRequest
import com.example.core.data.remote.response.BaseResponse
import com.example.core.data.remote.response.UserDataResponse
import io.reactivex.Single
import retrofit2.http.Body
import retrofit2.http.POST

interface ServiceApi {

    @POST("getBanks")
    fun fetchAllBanks(): Single<ApiResponse<BaseResponse>>

    @POST("secured/getUserData/v2")
    fun login(@Body body: BaseRequest): Single<ApiResponse<UserDataResponse>>
}