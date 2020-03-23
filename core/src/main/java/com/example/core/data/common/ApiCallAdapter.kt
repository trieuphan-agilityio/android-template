package com.example.core.data.common

import io.reactivex.Single
import io.reactivex.SingleObserver
import retrofit2.Call
import retrofit2.CallAdapter
import retrofit2.Callback
import retrofit2.Response
import java.lang.reflect.Type
import java.util.concurrent.atomic.AtomicBoolean

class ApiCallAdapter<R>(private val responseType: Type) : CallAdapter<R, Single<ApiResponse<R>>> {

    override fun responseType(): Type {
        return responseType
    }

    override fun adapt(call: Call<R>): Single<ApiResponse<R>> {
        return object : Single<ApiResponse<R>>() {
            var started = AtomicBoolean(false)

            override fun subscribeActual(observer: SingleObserver<in ApiResponse<R>>) {
                if (started.compareAndSet(false, true)) {
                    call.enqueue(object : Callback<R> {
                        override fun onResponse(call: Call<R>, response: Response<R>) {
                            observer.onSuccess(ApiResponse.create(response))
                        }

                        override fun onFailure(call: Call<R>, throwable: Throwable) {
                            observer.onSuccess(ApiResponse.create(throwable))
                        }
                    })
                }
            }
        }
    }
}