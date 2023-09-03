package com.example.search.data.repository

import com.example.helper.MockRetrofitProvider
import com.example.helper.TestUtil.mockResponse
import com.example.network.data.api.GithubApiService
import com.example.network.data.model.ApiResponse
import com.example.network.data.model.RequestException
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.runBlocking
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class SearchUserRepositoryTest() {
    private lateinit var searchUserRepository: SearchUserRepositoryImpl

    @get: Rule
    val server = MockWebServer()

    @Before
    fun setup() {
        val retrofit = MockRetrofitProvider.getRetrofitClient(server.url("/"))
        val apiService = retrofit.create(GithubApiService::class.java)

        searchUserRepository = SearchUserRepositoryImpl(apiService)
    }

    @After
    fun teardown() {
        server.shutdown()
    }

    @Test
    fun `searchUser returns ApiResponse type`() = runBlocking {
        server.enqueue(mockResponse("search_response.json").setResponseCode(200))
        val response = searchUserRepository.searchUser(
            query = "username",
            page = 1,
            perPage = 1
        )

        assertThat(response).isInstanceOf(ApiResponse::class.java)
        assertThat(response is ApiResponse.Success).isTrue()

        (response as ApiResponse.Success).responseData.run {
            assertThat(items).isNotEmpty()
            assertThat(items).hasSize(1)
        }
    }

    @Test
    fun `searchUser returns ApiResponse_Success with empty data`(): Unit = runBlocking {
        server.enqueue(mockResponse("search_empty_response.json").setResponseCode(200))
        val response = searchUserRepository.searchUser(
            query = "username",
            page = 1,
            perPage = 10
        )

        assertThat(response).isInstanceOf(ApiResponse::class.java)
        assertThat(response is ApiResponse.Success).isTrue()

        (response as ApiResponse.Success).responseData.run {
            assertThat(items).isEmpty()
        }
    }

    @Throws(Exception::class)
    @Test
    fun `searchUser returns error on a exception from api`(): Unit = runBlocking {
        server.enqueue(MockResponse().setResponseCode(400).setBody("Something went wrong!"))

        val response = searchUserRepository.searchUser(
            query = "username",
            page = 1,
            perPage = 10
        )

        assertThat(response).isInstanceOf(ApiResponse::class.java)
        assertThat(response is ApiResponse.Error).isTrue()
        (response as ApiResponse.Error).run {
            assertThat(response.exception).isInstanceOf(RequestException::class.java)
        }
    }
}
