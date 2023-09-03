package com.example.details.data.repository

import com.example.network.data.api.GithubApiService
import com.example.network.data.dto.UserDetailsDto
import com.example.network.data.model.ApiResponse
import com.example.network.utils.BaseApiInteractor
import javax.inject.Inject

class UserDetailsRepositoryImpl @Inject constructor(
    private val apiService: GithubApiService
) : BaseApiInteractor(), UserDetailsRepository {
    override suspend fun getUserDetails(username: String): ApiResponse<UserDetailsDto> {
        return safeApiCall {
            apiService.getUserDetails(username)
        }
    }
}
