package com.example.revoluttest.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.revoluttest.model.RatesModel
import com.example.revoluttest.model.RatesRepository
import com.example.revoluttest.model.RatesResponse
import com.fasterxml.jackson.core.type.TypeReference
import com.fasterxml.jackson.databind.ObjectMapper
import kotlinx.coroutines.launch
import java.util.*
import kotlin.concurrent.schedule


/**
 * This ViewModel holds a reference to LiveData which is returned  from the repository
 */
class RatesViewModel : ViewModel() {
    private val _rates = MutableLiveData<RatesModel>()
    val rates : LiveData<RatesModel>
        get() = _rates

    private val timerArray = ArrayList<Timer>()

    fun setupTimer(baseCurrency: String) {
        // if array is empty add a new timer
        if (timerArray.size == 0) timerArray.add(Timer())
        else timerArray[0] = Timer()

        timerArray[0].schedule(0,1000){
            viewModelScope.launch {
                val latestRates = getRates(baseCurrency)

                val transformedRates = latestRates?.let { transformDataclassToMap(it) }

                _rates.postValue(transformedRates)
            }
        }
    }

    suspend fun getRates(baseCurrency: String) = RatesRepository().getRates(baseCurrency)

    /**
     * Method that prepares the data to be used in the presentation layer.
     *
     * @param  RatesResponse type to be converted into an appropriate data class
     *
     * @return a map with all rates including the rate of the base currency as well
     *         as the baseCurrency in a separate field
     */
    private fun transformDataclassToMap (ratesResponse: RatesResponse) : RatesModel{

        val ratesResponseAsMap = ObjectMapper().convertValue(ratesResponse.rates, object:
            TypeReference<MutableMap<String, Any>>() {})

        ratesResponseAsMap[ratesResponse.baseCurrency.toLowerCase(Locale.ROOT)] = 1.00

        return RatesModel(ratesResponse.baseCurrency, ratesResponseAsMap)
    }

    fun stopCurrentTimer() {
        timerArray[0].cancel()
    }
}
