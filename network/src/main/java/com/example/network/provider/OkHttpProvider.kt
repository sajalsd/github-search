package com.example.network.provider

import okhttp3.Interceptor
import okhttp3.OkHttpClient
import java.util.concurrent.TimeUnit

object OkHttpProvider {
    private const val TIME_OUT = 45L
    private lateinit var okHttpClient: OkHttpClient
    fun getOkHttpClient(timeOut: Long = TIME_OUT, interceptors: Set<Interceptor>): OkHttpClient {
        if (!OkHttpProvider::okHttpClient.isInitialized) {
            val okHttpBuilder = OkHttpClient.Builder()
                .readTimeout(timeOut, TimeUnit.SECONDS)
                .writeTimeout(timeOut, TimeUnit.SECONDS)
                .apply {
                    interceptors.forEach {
                        addInterceptor(it)
                    }
                }

            okHttpClient = okHttpBuilder.build()
        }
        return okHttpClient
    }
}
