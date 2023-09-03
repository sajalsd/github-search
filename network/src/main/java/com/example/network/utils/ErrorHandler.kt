package com.example.network.utils

import com.example.network.data.dto.ErrorResponse
import com.example.network.data.model.GenericException
import com.example.network.data.model.NetworkException
import com.example.network.data.model.RequestException
import com.squareup.moshi.Moshi

object ErrorHandler {
    fun parseHttpException(throwable: Throwable): Exception {
        throwable.printStackTrace()
        return throwable.message?.let { message ->
            return@let NetworkException(message)
        } ?: run {
            return@run GenericException()
        }
    }

    fun parseCustomException(
        httpCode: Int,
        message: String? = null,
        errorBody: String? = null
    ): Exception {
        errorBody?.let { responseBody ->
            try {
                Moshi.Builder().build().adapter(ErrorResponse::class.java)
                    .lenient()
                    .fromJson(responseBody) ?.let { errorResponse ->
                        return RequestException(httpCode = httpCode, message = errorResponse.message)
                    }
            } catch (exception: Exception) {
                exception.printStackTrace()
            }
        }

        return message?.let { message ->
            return RequestException(httpCode = httpCode, message = message)
        } ?: run {
            return@run GenericException()
        }
    }
}
