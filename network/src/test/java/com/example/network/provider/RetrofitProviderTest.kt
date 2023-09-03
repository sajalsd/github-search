package com.example.network.provider

import com.google.common.truth.Truth.assertThat
import com.squareup.moshi.Moshi
import io.mockk.mockk
import okhttp3.OkHttpClient
import org.junit.Before
import org.junit.Test
import retrofit2.Retrofit

class RetrofitProviderTest {

    private val baseUrl: String = "https://example.com/"

    private lateinit var retrofitClient: Retrofit
    private val moshi: Moshi = mockk()
    private val okHttpClient: OkHttpClient = mockk()

    @Before
    fun setUp() {
        retrofitClient = RetrofitProvider.getRetrofitClient(baseUrl, okHttpClient, moshi)
    }

    @Test
    fun `getRetrofitClient returns RetrofitClient`() {
        assertThat(retrofitClient).isInstanceOf(Retrofit::class.java)
    }

    @Test
    fun `getRetrofitClient always returns same instance`() {
        val retrofitClient1 = RetrofitProvider.getRetrofitClient(baseUrl, okHttpClient, moshi)
        val retrofitClient2 = RetrofitProvider.getRetrofitClient("baseUrl", okHttpClient, moshi)
        val retrofitClient3 = RetrofitProvider.getRetrofitClient("/", okHttpClient, moshi)
        assertThat(retrofitClient1).isSameInstanceAs(retrofitClient2)
        assertThat(retrofitClient1).isSameInstanceAs(retrofitClient3)
    }

    @Test
    fun `retrofitClient sets proper base url`() {
        assertThat(retrofitClient.baseUrl().toString()).isEqualTo(baseUrl)
    }
}
