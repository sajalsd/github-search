package com.example.network.data

import com.example.network.data.api.GithubApiService
import com.example.network.helper.MockRetrofitProvider
import com.example.network.helper.TestUtil.mockResponse
import kotlinx.coroutines.runBlocking
import okhttp3.mockwebserver.MockWebServer
import org.junit.After
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class GithubApiServiceTest {
    private lateinit var gitApiService: GithubApiService

    @get: Rule
    val server = MockWebServer()

    @Before
    fun setUp() {
        val retrofit = MockRetrofitProvider.getRetrofitClient(server.url("/"))
        gitApiService = retrofit.create(GithubApiService::class.java)
    }

    @After
    fun tearDown() {
        server.shutdown()
    }

    @Test
    fun `searchUser calls with GET and query parameter`(): Unit = runBlocking {
        val searchQuery = "cat"
        val perPage = 10
        val page = 1

        server.enqueue(mockResponse("response.json"))

        gitApiService.searchUser(searchQuery, perPage, page)
        val request = server.takeRequest()

        Assert.assertEquals(request.method, "GET")
        Assert.assertEquals(request.requestUrl?.queryParameter("q"), searchQuery)
        Assert.assertEquals(request.requestUrl?.queryParameter("page"), page.toString())
        Assert.assertEquals(request.requestUrl?.queryParameter("per_page"), perPage.toString())
    }

    @Test
    fun `getUserDetails calls with GET and path parameter`(): Unit = runBlocking {
        val username = "test"

        server.enqueue(mockResponse("user_details_response.json"))

        gitApiService.getUserDetails(username)
        val request = server.takeRequest()

        Assert.assertEquals(request.method, "GET")
        Assert.assertTrue(request.requestUrl?.pathSegments?.contains(username) == true)
    }
}
