package com.example.core.data.remote

import com.example.core.data.common.ApiResponse
import com.example.core.data.remote.response.BaseResponse
import io.reactivex.Single
import retrofit2.http.POST

interface ServiceApi {

    @POST("getBanks")
    fun fetchAllBanks(): Single<ApiResponse<BaseResponse>>
}