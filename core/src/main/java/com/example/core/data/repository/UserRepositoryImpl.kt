package com.example.core.data.repository

import android.content.SharedPreferences
import com.example.core.data.local.AppDatabase
import com.example.core.data.local.dao.UserDao
import com.example.core.data.remote.ServiceApi
import com.example.core.data.remote.common.Result
import com.example.core.data.remote.request.BaseRequest
import com.example.core.data.remote.response.UserDataResponse
import com.example.core.data.local.extension.*
import io.reactivex.Single

class UserRepositoryImpl(
    database: AppDatabase,
    private val api: ServiceApi,
    private val sharedRef: SharedPreferences
) : UserRepository {
    private val userDao: UserDao = database.getUserDao()

    override fun login(body: BaseRequest): Single<Result<UserDataResponse>> {
        return api.login(body).map { response ->
            Result.fromResponse(response) { data ->
                data.user?.let { user ->
                    userDao.insertOrUpdate(user)
                    sharedRef.cacheUserAuthUser(user.userId)
                }
            }
        }
    }
}