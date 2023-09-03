package com.example.details.domain.usecase

import com.example.core.util.Resource
import com.example.details.domain.model.UserDetails
import kotlinx.coroutines.flow.Flow

interface GetUserDetailsUseCase {
    suspend fun getUserDetails(username: String): Flow<Resource<UserDetails>>
}
