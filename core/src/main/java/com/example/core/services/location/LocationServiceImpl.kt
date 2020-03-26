package com.example.core.services.location

import android.content.Context
import android.location.Location
import android.location.LocationManager

class LocationServiceImpl(context: Context) : LocationService {

    private val locationManager: LocationManager? = context.getSystemService(Context.LOCATION_SERVICE) as LocationManager?

    override fun isEnabled(): Boolean {
        if (locationManager == null) return false

        val isGPSEnabled = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)
        val isNetworkEnabled = locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)
        return isGPSEnabled || isNetworkEnabled
    }

    override fun startUpdate(onResult: (Location) -> Unit) {
        // TODO: Not implement yet
    }

    override fun getLastKnown(onResult: (Location?) -> Unit) {
        if (locationManager == null) {
            onResult.invoke(null)
            return
        }

        var location: Location? = null
        try {
            location = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER)
            if (location == null) {
                location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER)
            }
        } catch (e: SecurityException) {
            // Ignore
        }

        onResult.invoke(location)
    }

    override fun stopUpdate() {
        // TODO: Need to refactor
    }
}