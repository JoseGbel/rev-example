package com.example.revoluttest.model

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.example.revoluttest.network.RatesAPI
import com.example.revoluttest.network.RatesAPIFactory
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class RatesRepository {
    private val ratesApi: RatesAPI = RatesAPIFactory.createService()

    fun getRates(base: String): MutableLiveData<RatesResponse?> {

        val ratesData: MutableLiveData<RatesResponse?> = MutableLiveData()
        GlobalScope.launch {

            val call = ratesApi.getRates(base)

            Log.d("RETRORESPONSE", call.toString())

            call.enqueue(object : Callback<RatesResponse?> {
                override fun onResponse(
                    call: Call<RatesResponse?>?,
                    response: Response<RatesResponse?>
                ) {
                    Log.d("RETRORESPONSE", "OnSuccess!!")
                    if (response.isSuccessful) {
                        ratesData.value = response.body()
                    }
                }

                override fun onFailure(call: Call<RatesResponse?>?, t: Throwable?) {

                    Log.d("RETRORESPONSE", "OnFailure!!")
                    ratesData.value = null
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