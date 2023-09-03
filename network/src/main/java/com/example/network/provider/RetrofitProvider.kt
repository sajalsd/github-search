package com.example.network.provider

import com.squareup.moshi.Moshi
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

object RetrofitProvider {
    private const val BASE_URL = "https://api.github.com/"
    private lateinit var retrofitClient: Retrofit

    fun getRetrofitClient(baseUrl: String = BASE_URL, okHttpClient: OkHttpClient, moshi: Moshi): Retrofit {
        if (!RetrofitProvider::retrofitClient.isInitialized) {
            retrofitClient = Retrofit.Builder()
                .baseUrl(baseUrl)
                .client(okHttpClient)
                .addConverterFactory(MoshiConverterFactory.create(moshi).asLenient())
                .build()
        }

        return retrofitClient
    }
}
