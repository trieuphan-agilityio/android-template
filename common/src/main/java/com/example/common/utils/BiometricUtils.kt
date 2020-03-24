package com.example.common.utils

import android.content.Context
import androidx.biometric.BiometricManager.*
import android.os.Build

/**
 * Indicate whether this device can authenticate the user with biometrics
 * @return true if there are any available biometric sensors and biometrics are enrolled on the device, if not, return false
 */
fun canAuthenticate(context: Context): Int {
    return from(context).canAuthenticate()
}

/**
 * Checking the device has support biometrics
 * @return true if there device has support, if not return false
 */
fun hasSupportBiometric(context: Context): Boolean {
    return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
        val canAuthenticate = from(context).canAuthenticate()
        !(canAuthenticate == BIOMETRIC_ERROR_NO_HARDWARE || canAuthenticate == BIOMETRIC_ERROR_HW_UNAVAILABLE)
    } else {
        false
    }
}

/**
 * Checking the biometrics has enrolled by user.
 * @return true if user has enrolled, if not return false
 */
fun hasBiometricEnrolled(context: Context): Boolean {
    return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
        val canAuthenticate = from(context).canAuthenticate()
        (canAuthenticate == BIOMETRIC_SUCCESS)
    } else {
        false
    }
}