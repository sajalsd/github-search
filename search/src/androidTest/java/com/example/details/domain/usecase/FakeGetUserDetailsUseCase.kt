package com.example.details.domain.usecase

import com.example.core.util.AppError
import com.example.core.util.Resource
import com.example.core.util.UiText
import com.example.details.domain.model.UserDetails
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf

class FakeGetUserDetailsUseCase : GetUserDetailsUseCase {
    private val userDetails = UserDetails(
        name = "User 1",
        userName = "username",
        avatarUrl = "",
        bio = "",
        email = "",
        followers = 10,
        following = 10,
        location = ""
    )

    private var responseType = ResponseType.LOADING

    override suspend fun getUserDetails(username: String): Flow<Resource<UserDetails>> =
        flowOf(getUserDetails(responseType))

    fun setResponseType(responseType: ResponseType) {
        this.responseType = responseType
    }

    private fun getUserDetails(responseType: ResponseType): Resource<UserDetails> {
        return when (responseType) {
            ResponseType.ERROR -> Resource.Error<UserDetails>(
                AppError.GenericError(
                    errorMessage = UiText.DynamicString("error response test")
                )
            )

            ResponseType.LOADING -> Resource.Loading()
            ResponseType.SUCCESS -> Resource.Success(userDetails)
        }
    }
}

enum class ResponseType {
    ERROR, SUCCESS, LOADING
}
