package com.example.details.domain.repository

import com.example.network.data.dto.UserDetailsDto
import com.example.network.data.model.ApiResponse

interface UserDetailsRepository {
    suspend fun getUserDetails(username: String): ApiResponse<UserDetailsDto>
}
