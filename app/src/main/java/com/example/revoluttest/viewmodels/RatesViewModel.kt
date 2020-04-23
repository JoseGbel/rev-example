package com.example.revoluttest.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.revoluttest.model.RatesRepository
import com.example.revoluttest.model.RatesResponse

/**
 * This ViewModel holds a reference to LiveData which is returned  from the repository
 */
class RatesViewModel : ViewModel() {
    private lateinit var rates : MutableLiveData<RatesResponse>

    private lateinit var ratesRepository : RatesRepository

    fun getRatesRepository() : LiveData<RatesResponse>{
        return rates
    }

    fun init(base: String) {
        ratesRepository = RatesRepository.instance!!

        rates = ratesRepository.getRates(base)
    }
}
