package com.example.revoluttest.viewmodels

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.revoluttest.model.Currency
import com.example.revoluttest.model.RatesModel
import com.example.revoluttest.model.RatesRepository
import com.example.revoluttest.model.RatesResponse
import com.fasterxml.jackson.core.type.TypeReference
import com.fasterxml.jackson.databind.ObjectMapper
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.*
import kotlin.collections.ArrayList
import kotlin.concurrent.schedule


/**
 * This ViewModel holds a reference to LiveData which is returned  from the repository
 */
class RatesViewModel : ViewModel() {
    private val _rates = MutableLiveData<RatesModel>()
    val rates: LiveData<RatesModel>
        get() = _rates

    val ratesPositions = ArrayList<String>()

    private val timerArray = ArrayList<Timer>()

    fun setupTimer(baseCurrency: String, context: Context) {
        // if there is no timer in array is empty add a new timer
        if (timerArray.size == 0) timerArray.add(Timer())
        // otherwise re-create the timer
        else timerArray[0] = Timer()

        timerArray[0].schedule(0, 3000) {
            viewModelScope.launch {
                RatesRepository().getRates(baseCurrency, object : Callback<RatesResponse> {

                    override fun onResponse(
                        call: Call<RatesResponse>,
                        response: Response<RatesResponse>
                    ) {
                        if (response.code() == 200) {
                            val latestRates = response.body()!!
                            val transformedRates = transformDataclassToMap(latestRates)
                            _rates.postValue(transformedRates)
                        }
                    }

                    override fun onFailure(call: Call<RatesResponse>, t: Throwable) {

                    }
                })
            }
        }
    }

    /**
     * Method that uses the last positions recorded and sort the recreated list according to it
     */
    fun sortListByPositionsArray(ratesArray: ArrayList<Currency>) {
        val tempArray = ArrayList<Currency>()

        // Place sorted items in a temporary
        for (x in 0 until ratesArray.size) {

            var found = false
            for (y in 0 until ratesArray.size) {
                if (found) {
                    break
                }
                if (ratesPositions[x] == ratesArray[y].currencyName) {
                    tempArray.add(ratesArray[y])
                    found = true
                    if (x == 31) {
                        assert(true)

                    }

                }
            }
        }

        // Copy the temporary list back to the ratesArray
        for (x in 0 until tempArray.size) {
            ratesArray[x] = tempArray[x]
        }
    }

    fun initialisePositions() {
        ratesPositions.add("EUR")
        ratesPositions.add("AUD")
        ratesPositions.add("BGN")
        ratesPositions.add("BRL")
        ratesPositions.add("CAD")
        ratesPositions.add("CHF")
        ratesPositions.add("CNY")
        ratesPositions.add("CZK")
        ratesPositions.add("DKK")
        ratesPositions.add("GBP")
        ratesPositions.add("HKD")
        ratesPositions.add("HRK")
        ratesPositions.add("HUF")
        ratesPositions.add("IDR")
        ratesPositions.add("ILS")
        ratesPositions.add("INR")
        ratesPositions.add("ISK")
        ratesPositions.add("JPY")
        ratesPositions.add("KRW")
        ratesPositions.add("MXN")
        ratesPositions.add("MYR")
        ratesPositions.add("NOK")
        ratesPositions.add("NZD")
        ratesPositions.add("PHP")
        ratesPositions.add("PLN")
        ratesPositions.add("RON")
        ratesPositions.add("RUB")
        ratesPositions.add("SEK")
        ratesPositions.add("SGD")
        ratesPositions.add("THB")
        ratesPositions.add("USD")
        ratesPositions.add("ZAR")
    }

    fun recordPositions(ratesArray: ArrayList<Currency>): ArrayList<String> {
        for (x in 0 until ratesArray.size) {
            ratesPositions[x] = ratesArray[x].currencyName
        }

        return ratesPositions
    }

    /**
     * Takes the current baseCurrency and push it to the beggining of the list
     */
    fun moveBaseCurrencyToTopOf(array: ArrayList<Currency>, baseCurrency: String) {
        for (x in 0 until array.size) {
            if (array[x].currencyName == baseCurrency && x == 0) {
                return
            } else if (array[x].currencyName == baseCurrency) {
                val copy = array[x]
                for (j in (x - 1) downTo 0) {
                    array[j + 1] = array[j]
                }
                array[0] = copy
                return
            }
        }
    }

    /**
     * Method that prepares the data to be used in the presentation layer.
     *
     * @param  RatesResponse type to be converted into an appropriate data class
     *
     * @return a map with all rates including the rate of the base currency as well
     *         as the baseCurrency in a separate field
     */
    private fun transformDataclassToMap(ratesResponse: RatesResponse): RatesModel {

        val ratesResponseAsMap = ObjectMapper().convertValue(ratesResponse.rates, object :
            TypeReference<MutableMap<String, Any>>() {})

        ratesResponseAsMap[ratesResponse.baseCurrency.toLowerCase(Locale.ROOT)] = 1.00

        return RatesModel(ratesResponse.baseCurrency, ratesResponseAsMap)
    }

    fun stopCurrentTimer() {
        timerArray[0].cancel()
    }
}
