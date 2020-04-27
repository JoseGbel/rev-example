package com.example.revoluttest.model

import com.example.revoluttest.network.RatesAPI
import com.example.revoluttest.network.RatesAPIFactory
import retrofit2.Callback

/**
 * Repository class
 * Simply call the api and set up the callback
 */
class RatesRepository {
    private val ratesApi: RatesAPI = RatesAPIFactory.createService()

    fun getRates(base: String, callback: Callback<RatesResponse>) {
        val call = ratesApi.getRates(base)
        call.enqueue(callback)
    }
}