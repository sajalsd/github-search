package com.example.search.data.repository

import com.example.network.data.api.GithubApiService
import com.example.network.data.dto.SearchResponse
import com.example.network.data.model.ApiResponse
import com.example.network.utils.BaseApiInteractor
import com.example.search.domain.repository.SearchUserRepository
import javax.inject.Inject

class SearchUserRepositoryImpl @Inject constructor(
    private val api: GithubApiService
) : BaseApiInteractor(), SearchUserRepository {
    override suspend fun searchUser(query: String, perPage: Int, page: Int): ApiResponse<SearchResponse> {
        return safeApiCall {
            api.searchUser(query, perPage, page)
        }
    }
}
