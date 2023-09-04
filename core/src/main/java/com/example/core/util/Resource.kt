package com.example.core.util

sealed class Resource<out T>(val data: T? = null, val message: UiText? = null) {
    class Success<T>(data: T) : Resource<T>(data)
    class Error<T>(appError: AppError) : Resource<T>(message = appError.errorMessage)
    class Loading<T>(data: T? = null) : Resource<T>(data = data)
}
