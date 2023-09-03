package com.example.network.utils

import com.example.network.data.model.GenericException
import com.example.network.data.model.NetworkException
import com.example.network.data.model.RequestException
import com.google.common.truth.Truth.assertThat
import com.squareup.moshi.Moshi
import org.junit.Test
import java.net.UnknownHostException

class ErrorHandlerTest {
    private val errorHandler = ErrorHandler(Moshi.Builder().build())

    @Test
    fun `parseHttpException return NetworkException for all proper http exception`() {
        val unknownHostException = UnknownHostException()
        assertThat(errorHandler.parseHttpException(unknownHostException))
            .isInstanceOf(NetworkException::class.java)

        val throwable = Throwable("Another exception")
        assertThat(errorHandler.parseHttpException(throwable)).isInstanceOf(NetworkException::class.java)
    }

    @Test
    fun `parseHttpException return GenericException when message is null`() {
        val throwable = Throwable()
        assertThat(errorHandler.parseHttpException(throwable)).isInstanceOf(GenericException::class.java)
    }

    @Test
    fun `parseCustomException return GenericException when ResponseBody and message is null`() {
        assertThat(errorHandler.parseCustomException(httpCode = 400)).isInstanceOf(
            GenericException::class.java
        )
    }

    @Test
    fun `parseCustomException return RequestException when ResponseBody null but message is present`() {
        assertThat(
            errorHandler.parseCustomException(
                httpCode = 400,
                message = "Something went wrong"
            )
        ).isInstanceOf(
            RequestException::class.java
        )
    }
}
