package com.example.core.services.location

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent

class LocationBroadcastReceiver(private val onChanged: (() -> Unit)? = null) : BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent?) {
        intent?.let { intent ->
            if (intent.action == "android.location.PROVIDERS_CHANGED") {
                onChanged?.invoke()
            }
        }
    }
}