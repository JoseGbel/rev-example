package com.example.revoluttest.network

import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

/**
 * Factory which creates Retrofit instances for the application's REST API
 */
object RatesAPIFactory {
    private const val API_BASE_URL = "https://hiring.revolut.codes"

    fun createService(): RatesAPI {
        val logging = HttpLoggingInterceptor()
        logging.apply { logging.level = HttpLoggingInterceptor.Level.BODY }
        val httpClient = OkHttpClient.Builder().addInterceptor(logging)

        val builder = Retrofit.Builder()
            .baseUrl(API_BASE_URL)
            .addConverterFactory(GsonConverterFactory.create(GsonBuilder().create()))

        return builder
            .client(httpClient.build())
            .build().create(RatesAPI::class.java)
    }
}