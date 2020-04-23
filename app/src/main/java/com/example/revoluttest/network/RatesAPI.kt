package com.example.revoluttest.network

import com.example.revoluttest.model.RatesResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

/**
 * Service interface which contains REST Calls to use with Retrofit
 */
interface RatesAPI {
    @GET("latest")
    suspend fun getRates(@Query("base") base: String) : RatesResponse
}