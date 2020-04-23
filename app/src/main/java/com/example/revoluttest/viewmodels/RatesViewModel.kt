package com.example.revoluttest.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.revoluttest.model.RatesRepository
import com.example.revoluttest.model.RatesResponse
import kotlinx.coroutines.*
import java.util.*
import kotlin.concurrent.timer

/**
 * This ViewModel holds a reference to LiveData which is returned  from the repository
 */
class RatesViewModel : ViewModel() {
    private val _rates = MutableLiveData<RatesResponse>()

    val rates : LiveData<RatesResponse>
        get() = _rates

    fun init(base: String) {
        timer("NetworkRequest",false, 0, 1000) {
            viewModelScope.launch {
                _rates.postValue(RatesRepository().getRates(base))

            }
        }
    }
}
