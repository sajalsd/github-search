package com.example.search.domain.usecase

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.helper.ResponseUtil
import com.example.helper.collectDataForTest
import com.example.helper.getMockPagingData
import com.example.search.domain.model.toUser
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.TestCoroutineScheduler
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule
import org.mockito.Mockito

class SearchUserUseCaseTest {
    private val searchUserUseCase = Mockito.mock(SearchUserUseCaseImpl::class.java)

    @Rule
    @JvmField
    var rule: TestRule = InstantTaskExecutorRule()

    private val scheduler = TestCoroutineScheduler()
    private val dispatcher = StandardTestDispatcher(scheduler)

    @Before
    fun setup() {
        Dispatchers.setMain(dispatcher)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `searchUser returns paging data on success`() = runTest {
        val searchResponse = ResponseUtil.getMockSearchResponse("search_response.json")
        Mockito.`when`(searchUserUseCase.searchUser("user"))
            .thenReturn(getMockPagingData(searchResponse.items.map { it.toUser() }))

        searchUserUseCase.searchUser("user").collectLatest { pagingData ->
            val gifUiModels = pagingData.collectDataForTest()
            assertThat(gifUiModels).hasSize(1)
        }
    }

    @Test
    fun `searchGif can returns empty paging data`() = runTest {
        val searchResponse = ResponseUtil.getMockSearchResponse("search_empty_response.json")
        Mockito.`when`(searchUserUseCase.searchUser("user"))
            .thenReturn(getMockPagingData(searchResponse.items.map { it.toUser() }))

        searchUserUseCase.searchUser("user").collectLatest { pagingData ->
            val searchItems = pagingData.collectDataForTest()
            assertThat(pagingData).isNotNull()
            assertThat(searchItems).isEmpty()
        }
    }
}
