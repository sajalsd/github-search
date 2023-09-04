package com.example.details.domain.usecase

import com.example.core.R
import com.example.core.util.AppError
import com.example.core.util.Resource
import com.example.core.util.UiText
import com.example.details.domain.model.UserDetails
import com.example.details.domain.model.toUserDetails
import com.example.details.domain.repository.UserDetailsRepository
import com.example.network.data.model.ApiResponse
import com.example.network.data.model.ConnectionException
import com.example.network.data.model.RequestException
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GetUserDetailsUseCaseImpl @Inject constructor(
    private val userDetailsRepository: UserDetailsRepository
) : GetUserDetailsUseCase {
    override suspend fun getUserDetails(username: String): Flow<Resource<UserDetails>> = flow {
        emit(Resource.Loading())
        when (val result = userDetailsRepository.getUserDetails(username)) {
            is ApiResponse.Success -> {
                emit(Resource.Success(result.responseData.toUserDetails()))
            }
            is ApiResponse.Error -> {
                val appError = when (val exception = result.exception) {
                    is ConnectionException -> AppError.NoInternet(UiText.StringResource(R.string.no_internet_msg))
                    is RequestException -> AppError.ApiError(UiText.DynamicString(exception.message))
                    else -> AppError.GenericError(UiText.StringResource(R.string.generic_error_msg))
                }
                emit(Resource.Error(appError))
            }
        }
    }
}
