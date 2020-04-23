package com.example.revoluttest.network

import okio.Timeout
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.IOException

class ResultCall<T>(proxy: Call<T>) : CallDelegate<T, Result<T>>(proxy) {
    override fun enqueueImpl(callback: Callback<Result<T>>) = proxy.enqueue(object: Callback<T> {
        override fun onResponse(call: Call<T>, response: Response<T>) {
            val code = response.code()
            val result = if (code in 200 until 300) {
                val body = response.body()
                Result.Success(body)
            } else {
                Result.Failure(code)
            }

            callback.onResponse(this@ResultCall, Response.success(result))
        }

        override fun onFailure(call: Call<T>, t: Throwable) {
            val result = if (t is IOException) {
                Result.NetworkError
            } else {
                Result.Failure(null)
            }

            callback.onResponse(this@ResultCall, Response.success(result))
        }
    })

    override fun cloneImpl() = ResultCall(proxy.clone())
    override fun timeout(): Timeout {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}