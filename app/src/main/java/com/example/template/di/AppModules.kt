package com.example.template.di

import android.content.Context
import com.example.core.data.local.AppDatabase
import com.example.core.data.remote.ServiceFactory
import com.example.core.data.repository.UserRepository
import com.example.core.data.repository.UserRepositoryImpl
import com.example.core.services.location.LocationService
import com.example.core.services.location.LocationServiceImpl
import com.example.template.BuildConfig
import com.example.template.ui.auth.LoginViewModel
import com.example.template.ui.splash.SplashViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val repositoryModule = module {
    single<UserRepository> { UserRepositoryImpl(get(), get(), get()) }
}

val networkModule = module {
    // Define a singleton of type HttpClient
    // inject property "server_url" from Koin properties
    single { ServiceFactory.getService(androidContext(), BuildConfig.BASE_URL) }
}

val localModule = module {
    single { AppDatabase.getDatabase(context = androidContext(), dbName = "AppConfig.DB_NAME") }
    single { androidContext().getSharedPreferences("AppConfig.REF_NAME", Context.MODE_PRIVATE) }
}

val viewModelModule = module {
    viewModel { SplashViewModel() }
    viewModel { LoginViewModel(get()) }
}

val serviceModule = module {
    single<LocationService> { LocationServiceImpl(get()) }
}

