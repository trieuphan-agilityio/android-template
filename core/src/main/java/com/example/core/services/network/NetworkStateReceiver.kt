package com.example.core.services.network

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager

class NetworkStateReceiver (val onStateChanged: ((isConnected: Boolean) -> Unit)? = null): BroadcastReceiver() {

    override fun onReceive(context: Context?, intent: Intent?) {
        val cm: ConnectivityManager = context?.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val activeNetwork = cm.activeNetworkInfo
        val isConnected: Boolean = activeNetwork?.isConnected == true
        onStateChanged?.invoke(isConnected)
    }
}