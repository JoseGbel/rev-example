package com.example.revoluttest.model

/**
 * Simple response object that contains the JSON structure received on the
 * response of the network call
 */
data class RatesResponse(
    val baseCurrency: String,
    val rates: Rates
)