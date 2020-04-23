package com.example.revoluttest

import android.content.Context
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.work.*
import com.example.revoluttest.model.RatesRepository
import com.example.revoluttest.model.RatesResponse
import com.example.revoluttest.network.RatesAPI
import com.example.revoluttest.network.RatesAPIFactory
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.*
import java.util.concurrent.TimeUnit

class DataFetchingWorker(context: Context, workerParams: WorkerParameters, val repository: RatesRepository, val base: String) :
    Worker(context, workerParams) {

    override fun doWork(): Result {
        networkCall()

        return Result.success()
    }

    private fun networkCall() : LiveData<RatesResponse> {
        Log.d("MYTAG", "Doing some work")
        return repository.getRates(base)
    }

    companion object {
        private fun setConstraints() : Constraints{
             return Constraints.Builder()
                .build()
        }

        fun periodicWork(){
            val periodicWork = PeriodicWorkRequest.Builder(DataFetchingWorker::class.java, 1, TimeUnit.SECONDS)
                .setInitialDelay(1, TimeUnit.SECONDS)
                .setConstraints(setConstraints())
                .build()

            WorkManager.getInstance().enqueueUniquePeriodicWork("periodic", ExistingPeriodicWorkPolicy.REPLACE, periodicWork)
        }
    }
}