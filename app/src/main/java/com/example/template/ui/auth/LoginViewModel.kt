package com.example.template.ui.auth

import androidx.lifecycle.MutableLiveData
import com.example.common.extensions.applyIoScheduler
import com.example.core.data.remote.common.Result
import com.example.core.data.remote.request.BaseRequest
import com.example.core.data.remote.response.UserDataResponse
import com.example.core.data.repository.UserRepository
import com.example.template.ui.common.base.BaseViewModel
import io.reactivex.rxkotlin.addTo

class LoginViewModel(private val userRepo: UserRepository) : BaseViewModel() {
    val userData = MutableLiveData<Result<UserDataResponse>>()

    /**
     * Login normal with username and password or fingerprint
     *
     * @param body request body
     */
    fun login(body: BaseRequest) {
        userRepo.login(body)
            .applyIoScheduler()
            .subscribe { resource ->
                userData.postValue(resource)
            }.addTo(compositeDisposable)
    }
}