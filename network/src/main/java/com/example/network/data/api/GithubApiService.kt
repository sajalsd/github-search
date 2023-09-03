package com.example.network.data.api

import com.example.network.data.dto.SearchResponse
import com.example.network.data.dto.UserDetailsDto
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface GithubApiService {
    @GET("/search/users")
    suspend fun searchUser(
        @Query("q") query: String,
        @Query("per_page") perPage: Int,
        @Query("page") page: Int
    ): Response<SearchResponse>

    @GET("/users/{username}")
    suspend fun getUser(@Path("username") userName: String): Response<UserDetailsDto>
}
