package com.example.network.utils

import com.example.network.data.model.ApiResponse
import com.example.network.data.model.CHECK_INTERNET_MSG
import com.example.network.data.model.ConnectionException
import com.example.network.data.model.EmptyResponseBodyException
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.Response
import java.io.IOException

abstract class BaseApiInteractor {

    protected suspend fun <T : Any> safeApiCall(
        dispatcher: CoroutineDispatcher = Dispatchers.IO,
        apiCall: suspend () -> Response<T>
    ): ApiResponse<T> {
        var response: Response<T>

        return withContext(dispatcher) {
            try {
                response = apiCall()
            } catch (ioException: IOException) {
                return@withContext ApiResponse.Error(
                    ConnectionException(
                        message = ioException.message ?: CHECK_INTERNET_MSG
                    )
                )
            } catch (httpException: Throwable) {
                return@withContext ApiResponse.Error(ErrorHandler.parseHttpException(httpException))
            }

            response.let {
                if (response.isSuccessful) {
                    response.body()?.let { body ->
                        ApiResponse.Success(body)
                    } ?: ApiResponse.Error(EmptyResponseBodyException())
                } else {
                    ApiResponse.Error(
                        ErrorHandler.parseCustomException(
                            httpCode = response.code(),
                            message = response.message(),
                            errorBody = response.errorBody()?.string()
                        )
                    )
                }
            }
        }
    }
}
