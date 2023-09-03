package com.example.network.provider

import com.google.common.truth.Truth.assertThat
import io.mockk.mockk
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import org.junit.Before
import org.junit.Test

class OkHttpProviderTest {
    private val timeOutInSec = 40L
    private lateinit var okHttpClient: OkHttpClient
    private val interceptor: Interceptor = mockk()

    @Before
    fun setUp() {
        okHttpClient = OkHttpProvider.getOkHttpClient(timeOutInSec, setOf(interceptor))
    }

    @Test
    fun `getOkHttpClient returns OkHttpClient`() {
        assertThat(okHttpClient).isInstanceOf(OkHttpClient::class.java)
    }

    @Test
    fun `getOkHttpClient always returns sameClient`() {
        val okHttpClient1 = OkHttpProvider.getOkHttpClient(timeOutInSec, setOf(interceptor))
        val okHttpClient2 = OkHttpProvider.getOkHttpClient(timeOutInSec, setOf(interceptor))
        assertThat(okHttpClient1).isSameInstanceAs(okHttpClient2)
    }

    @Test
    fun `okHttpClient set and returns timeOut`() {
        assertThat(okHttpClient.readTimeoutMillis).isEqualTo(timeOutInSec * 1000)
        assertThat(okHttpClient.writeTimeoutMillis).isEqualTo(timeOutInSec * 1000)
    }

    @Test
    fun `okHttpClient has loggingInterceptor`() {
        val interceptor = okHttpClient.interceptors

        assertThat(interceptor).isNotEmpty()
        assertThat(interceptor.first()).isInstanceOf(Interceptor::class.java)
    }
}
