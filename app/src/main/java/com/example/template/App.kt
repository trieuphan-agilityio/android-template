package com.example.template

import android.app.Activity
import android.app.Application
import android.content.IntentFilter
import android.os.Build
import android.os.Bundle
import com.example.core.services.location.LocationBroadcastReceiver
import com.example.core.services.location.LocationService
import com.example.core.services.network.NetworkStateReceiver
import com.example.template.di.*
import com.ice.restring.Restring
import io.reactivex.plugins.RxJavaPlugins
import org.koin.android.ext.android.inject
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import timber.log.Timber

class App : Application() {

    private var currentActivity: Activity? = null
    private var isForeground: Boolean = false
    private val locationService: LocationService by inject()

    override fun onCreate() {
        super.onCreate()

        // Start Koin
        startKoin {
            androidLogger()
            androidContext(this@App)

            modules(
                listOf(
                    networkModule,
                    localModule,
                    repositoryModule,
                    viewModelModule,
                    serviceModule
                )
            )
        }

        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }

        // Through UpStream-DownStream exceptions
        RxJavaPlugins.setErrorHandler(Throwable::printStackTrace)

        // Initialize Restring
        Restring.init(this)

        // Register activity lifecycle callback
        registerActivityLifecycleCallbacks(activityLifecycleCallbacks)

        // Monitoring internet connection state
        registerConnectionReceiver()

        // Monitoring location service provider changes
        registerLocationReceiver()

        // Try to get last known location when open the app.
        if (locationService.isEnabled()) {
            locationService.getLastKnown {
                Timber.d("Location: ${it?.latitude}/${it?.longitude}")
            }
        }
    }

    private fun registerConnectionReceiver() {
        val networkStateReceiver = NetworkStateReceiver(onStateChanged = onNetworkStateChange)
        val intentFilter = IntentFilter()
        intentFilter.addAction("android.net.conn.CONNECTIVITY_CHANGE")
        registerReceiver(networkStateReceiver, intentFilter)
    }

    private fun registerLocationReceiver() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            val locationBroadcastReceiver = LocationBroadcastReceiver(onChanged = {
                Timber.d("Location service provider changed")
                if (locationService.isEnabled()) {
                    locationService.getLastKnown {
                        Timber.d("Location: ${it?.latitude}/${it?.longitude}")
                    }
                }
            })
            val intentFilter = IntentFilter()
            intentFilter.addAction("android.location.PROVIDERS_CHANGED")
            registerReceiver(locationBroadcastReceiver, intentFilter)
        }
    }

    /**
     * Monitoring network connection state
     */
    private val onNetworkStateChange: ((Boolean) -> Unit) = { isConnected ->
        if (!isConnected && currentActivity != null && isForeground) {
           // FIXME: show alert lost network
        }
    }


    ///////////////////////////////////////////////////////////////////////////////////////////////
    // ActivityLifecycleCallbacks
    ///////////////////////////////////////////////////////////////////////////////////////////////
    private val activityLifecycleCallbacks = object: ActivityLifecycleCallbacks {
        override fun onActivityPaused(activity: Activity?) {
            isForeground = false
        }

        override fun onActivityResumed(activity: Activity?) {
            isForeground = true
            currentActivity = activity
        }

        override fun onActivityStarted(activity: Activity?) {
            currentActivity = activity
        }

        override fun onActivityDestroyed(activity: Activity?) {
            currentActivity = null
        }

        override fun onActivitySaveInstanceState(activity: Activity?, outState: Bundle?) {

        }

        override fun onActivityStopped(activity: Activity?) {

        }

        override fun onActivityCreated(activity: Activity?, savedInstanceState: Bundle?) {
            currentActivity = activity
        }
    }
}