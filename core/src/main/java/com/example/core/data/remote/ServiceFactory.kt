package com.example.core.data.remote

import android.content.Context
import com.example.core.BuildConfig
import com.example.core.data.common.ApiCallAdapterFactory
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

class ServiceFactory {

    companion object {

        @Volatile
        private var INSTANCE: ServiceApi? = null

        fun getService(context: Context, baseUrl: String): ServiceApi {
            return INSTANCE ?: synchronized(this) {
                val instance = Retrofit.Builder()
                    .baseUrl(baseUrl)
                    .client(provideOkHttpClient(context))
                    .addConverterFactory(GsonConverterFactory.create())
                    .addCallAdapterFactory(ApiCallAdapterFactory())
                    .build()
                    .create(ServiceApi::class.java)
                INSTANCE = instance
                instance
            }
        }

        private fun provideOkHttpClient(context: Context): OkHttpClient {
            val logging = HttpLoggingInterceptor().apply {
                level = if (BuildConfig.DEBUG) HttpLoggingInterceptor.Level.BODY else HttpLoggingInterceptor.Level.NONE
            }

            return OkHttpClient.Builder().apply {
                addInterceptor(logging)
                addInterceptor(MockInterceptor(context)) // FIXME: Will remove when integrated full APIs.
                connectTimeout(120, TimeUnit.SECONDS)
                readTimeout(120, TimeUnit.SECONDS)
                writeTimeout(90, TimeUnit.SECONDS).build()
            }.build()
        }
    }
}