package com.example.revoluttest.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.revoluttest.model.RatesRepository
import com.example.revoluttest.model.RatesResponse
import kotlinx.coroutines.*
import kotlin.concurrent.timer

/**
 * This ViewModel holds a reference to LiveData which is returned  from the repository
 */
class RatesViewModel : ViewModel() {
    private val _rates = MutableLiveData<RatesResponse>()
    val rates : LiveData<RatesResponse>
        get() = _rates

    fun setupTimer(baseCurrency: String) {
        timer("NetworkRequest",false, 0, 1000) {
            viewModelScope.launch {
                val latestRates = getRates(baseCurrency)

                _rates.postValue(latestRates)

            }
        }
    }

    suspend fun getRates(baseCurrency: String) = RatesRepository().getRates(baseCurrency)
}
