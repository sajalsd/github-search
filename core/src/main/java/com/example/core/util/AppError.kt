package com.example.core.util

sealed class AppError(open val errorMessage: UiText) {
    data class NoInternet(override val errorMessage: UiText) : AppError(errorMessage)
    data class ApiError(override val errorMessage: UiText) : AppError(errorMessage)
    data class GenericError(override val errorMessage: UiText) : AppError(errorMessage)
}
