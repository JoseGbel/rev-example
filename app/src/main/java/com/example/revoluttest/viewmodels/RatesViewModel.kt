package com.example.revoluttest.viewmodels

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.revoluttest.model.RatesRepository
import com.example.revoluttest.model.RatesResponse

class RatesViewModel : ViewModel() {
    private val _rates = MutableLiveData<RatesResponse>()

    private lateinit var ratesRepository : RatesRepository

    val rates : LiveData<RatesResponse>
        get() = _rates

    fun getRatesRepository() : LiveData<RatesResponse>{
        return _rates
    }

    fun init(base: String) {
//        if (_rates != null) {
//            return
//        }
        ratesRepository = RatesRepository.instance!!
//        _rates.postValue(ratesRepository.getRates(base).value)
        _rates.value = ratesRepository.getRates(base).value
        Log.d("RETRORESPONSE", "Rates: " + _rates.value.toString())
    }
}
