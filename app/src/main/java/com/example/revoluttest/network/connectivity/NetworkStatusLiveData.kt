package com.example.revoluttest.network.connectivity

import android.app.Application
import android.content.Context
import android.net.*
import android.os.Build
import androidx.lifecycle.LiveData

/**
 * This object is used to handled network connectivity issues.
 * It implements LiveData to get the current status of a network
 */
object NetworkStatusLiveData : LiveData<NetworkStatus>() {
    private lateinit var application: Application

    private lateinit var networkRequest: NetworkRequest

    fun init(application: Application) {
        this.application = application
        networkRequest = NetworkRequest.Builder()
            .addTransportType(NetworkCapabilities.TRANSPORT_CELLULAR)
            .addTransportType(NetworkCapabilities.TRANSPORT_WIFI)
            .build()
    }

    override fun onActive() {
        super.onActive()
        getStatus()
    }

    private fun getStatus() {
        val cm = application.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val callback = object : ConnectivityManager.NetworkCallback() {
                override fun onAvailable(network: Network) {
                    super.onAvailable(network)
                    postValue(NetworkStatus.AVAILABLE)
                }

                override fun onUnavailable() {
                    super.onUnavailable()
                    postValue(NetworkStatus.UNAVAILABLE)
                }

                override fun onLost(network: Network) {
                    super.onLost(network)
                    postValue(NetworkStatus.LOST)
                }
            }

            cm.requestNetwork(networkRequest, callback, 3000)
        } else {
            cm.registerNetworkCallback(networkRequest, object : ConnectivityManager.NetworkCallback() {
                override fun onAvailable(network: Network) {
                    super.onAvailable(network)
                    postValue(NetworkStatus.AVAILABLE)
                }

                override fun onUnavailable() {
                    super.onUnavailable()
                    postValue(NetworkStatus.UNAVAILABLE)
                }

                override fun onLost(network: Network) {
                    super.onLost(network)
                    postValue(NetworkStatus.LOST)
                }
            })
        }
    }
}

enum class NetworkStatus {
    AVAILABLE, UNAVAILABLE, LOST
}