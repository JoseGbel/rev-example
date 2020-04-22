package com.example.revoluttest.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class RatesResponse(@SerializedName ("baseCurrency") @Expose val baseCurrency: String,
                    @SerializedName("rates") @Expose val rates : List<Rate>)
