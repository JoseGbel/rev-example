package com.example.revoluttest.model

import androidx.lifecycle.MutableLiveData
import com.example.revoluttest.network.RatesAPI
import com.example.revoluttest.network.RatesAPIFactory
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.net.SocketTimeoutException

/**
 * Repository class
 * This class contains a companion object to create a Singleton of a Rates Repository
 */
class RatesRepository {
    private val ratesApi: RatesAPI = RatesAPIFactory.createService()

    suspend fun getRates(base: String): RatesResponse? {
            return ratesApi.getRates(base)
    }
}