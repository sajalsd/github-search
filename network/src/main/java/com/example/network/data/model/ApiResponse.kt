package com.example.network.data.model

sealed class ApiResponse<out T : Any> {
    data class Success<out T : Any>(val responseData: T) : ApiResponse<T>()
    data class Error(val exception: Exception) : ApiResponse<Nothing>()
}
