package com.example.network.utils

import com.example.network.data.model.GenericException
import com.example.network.data.model.NetworkException
import com.example.network.data.model.RequestException
import com.google.common.truth.Truth.assertThat
import org.junit.Test
import java.net.UnknownHostException

class ErrorHandlerTest {

    @Test
    fun `parseHttpException return NetworkException for all proper http exception`() {
        val unknownHostException = UnknownHostException()
        assertThat(ErrorHandler.parseHttpException(unknownHostException))
            .isInstanceOf(NetworkException::class.java)

        val throwable = Throwable("Another exception")
        assertThat(ErrorHandler.parseHttpException(throwable)).isInstanceOf(NetworkException::class.java)
    }

    @Test
    fun `parseHttpException return GenericException when message is null`() {
        val throwable = Throwable()
        assertThat(ErrorHandler.parseHttpException(throwable)).isInstanceOf(GenericException::class.java)
    }

    @Test
    fun `parseCustomException return GenericException when ResponseBody and message is null`() {
        assertThat(ErrorHandler.parseCustomException(httpCode = 400)).isInstanceOf(
            GenericException::class.java
        )
    }

    @Test
    fun `parseCustomException return RequestException when ResponseBody null but message is present`() {
        assertThat(
            ErrorHandler.parseCustomException(
                httpCode = 400,
                message = "Something went wrong"
            )
        ).isInstanceOf(
            RequestException::class.java
        )
    }
}
