package com.example.search.domain.repository

import com.example.network.data.dto.SearchResponse
import com.example.network.data.model.ApiResponse

interface SearchUserRepository {
    suspend fun searchUser(query: String, perPage: Int, page: Int): ApiResponse<SearchResponse>
}
