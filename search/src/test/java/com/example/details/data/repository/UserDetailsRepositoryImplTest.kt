package com.example.details.data.repository

import com.example.helper.MockRetrofitProvider
import com.example.helper.TestUtil
import com.example.network.data.api.GithubApiService
import com.example.network.data.dto.UserDetailsDto
import com.example.network.data.model.ApiResponse
import com.example.network.data.model.RequestException
import com.google.common.truth.Truth
import kotlinx.coroutines.runBlocking
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class UserDetailsRepositoryImplTest() {
    private lateinit var userDetailsRepository: UserDetailsRepositoryImpl

    @get: Rule
    val server = MockWebServer()

    @Before
    fun setup() {
        val retrofit = MockRetrofitProvider.getRetrofitClient(server.url("/"))
        val apiService = retrofit.create(GithubApiService::class.java)

        userDetailsRepository = UserDetailsRepositoryImpl(apiService)
    }

    @After
    fun teardown() {
        server.shutdown()
    }

    @Test
    fun `getUserDetails returns ApiResponse type`(): Unit = runBlocking {
        server.enqueue(TestUtil.mockResponse("user_details_response.json").setResponseCode(200))
        val response = userDetailsRepository.getUserDetails("username")

        Truth.assertThat(response).isInstanceOf(ApiResponse::class.java)
        Truth.assertThat(response is ApiResponse.Success).isTrue()

        (response as ApiResponse.Success).responseData.run {
            Truth.assertThat(this).isInstanceOf(UserDetailsDto::class.java)
        }
    }

    @Throws(Exception::class)
    @Test
    fun `searchUser returns error on a exception from api`(): Unit = runBlocking {
        server.enqueue(MockResponse().setResponseCode(400).setBody("Something went wrong!"))

        val response = userDetailsRepository.getUserDetails("username")

        Truth.assertThat(response).isInstanceOf(ApiResponse::class.java)
        Truth.assertThat(response is ApiResponse.Error).isTrue()

        (response as ApiResponse.Error).run {
            Truth.assertThat(response.exception).isInstanceOf(RequestException::class.java)
        }
    }
}
