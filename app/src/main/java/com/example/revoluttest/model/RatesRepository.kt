package com.example.revoluttest.model

import com.example.revoluttest.network.RatesAPI
import com.example.revoluttest.network.RatesAPIFactory
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

/**
 * Repository class
 * This class contains a companion object to create a Singleton of a Rates Repository
 */
class RatesRepository {


    private val ratesApi: RatesAPI = RatesAPIFactory.createService()

    fun getRates(base: String): RatesResponse? {
        var ratesData : RatesResponse? = null
        GlobalScope.launch (Dispatchers.Main) {
            val call = ratesApi.getRates(base)

            call.enqueue(object : Callback<RatesResponse?> {
                override fun onResponse(
                    call: Call<RatesResponse?>?,
                    response: Response<RatesResponse?>
                ) {
//                    if (response.isSuccessful) {
                        ratesData = response.body()
//                    }
                }

                override fun onFailure(call: Call<RatesResponse?>?, t: Throwable?) {

                }
            })
        }
        return ratesData
    }

    companion object {
        private var ratesRepository: RatesRepository? = null
        val instance: RatesRepository?
            get() {
                if (ratesRepository == null) {
                    ratesRepository = RatesRepository()
                }
                return ratesRepository
            }
    }

}