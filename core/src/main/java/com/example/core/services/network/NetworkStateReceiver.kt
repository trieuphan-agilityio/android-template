package com.example.core.services.network

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkRequest

class NetworkStateReceiver (val onStateChanged: ((isConnected: Boolean) -> Unit)? = null): BroadcastReceiver() {

    override fun onReceive(context: Context?, intent: Intent?) {
        val cm: ConnectivityManager =
            context?.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val builder: NetworkRequest.Builder = NetworkRequest.Builder()
        cm.registerNetworkCallback(
            builder.build(),
            object : ConnectivityManager.NetworkCallback() {

                override fun onAvailable(network: Network) {
                    onStateChanged?.invoke(true)
                }

                override fun onLost(network: Network) {
                    onStateChanged?.invoke(false)
                }
            }
        )
    }
}