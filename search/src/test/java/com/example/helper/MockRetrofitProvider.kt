package com.example.helper

import okhttp3.Dispatcher
import okhttp3.HttpUrl
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

object MockRetrofitProvider {
    fun getRetrofitClient(url: HttpUrl): Retrofit {
        val okHttpClient = OkHttpClient.Builder()
            .dispatcher(Dispatcher(TestUtil.immediateExecutorService()))
            .build()

        return Retrofit.Builder()
            .baseUrl(url)
            .client(okHttpClient)
            .addConverterFactory(MoshiConverterFactory.create())
            .build()
    }
}
