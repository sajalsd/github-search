package com.example.search.domain.usecase

import androidx.paging.PagingData
import com.example.search.domain.model.User
import kotlinx.coroutines.flow.Flow

interface SearchUserUseCase {
    suspend fun searchUser(query: String): Flow<PagingData<User>>
}
