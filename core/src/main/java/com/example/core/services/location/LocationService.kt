package com.example.core.services.location

import android.location.Location

interface LocationService {

    /**
     * Check location service is enabled or not
     *
     * @return True if the the location service is enabled
     */
    fun isEnabled(): Boolean

    /**
     * Initiates location retrieval process to receive only one location update.
     *
     * @param onResult Provides a success block which will grant access to retrieved location
     */
    fun getLastKnown(onResult: (Location?) -> Unit)

    /**
     * Start update location changed
     *
     * @param onResult The result callback function
     */
    fun startUpdate(onResult: (Location) -> Unit)

    /**
     * Stop updating location changes
     */
    fun stopUpdate()
}