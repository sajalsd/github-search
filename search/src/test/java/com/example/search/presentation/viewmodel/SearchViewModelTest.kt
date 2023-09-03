package com.example.search.presentation.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import androidx.paging.PagingData
import com.example.helper.ResponseUtil.getMockSearchResponse
import com.example.helper.argumentCaptor
import com.example.helper.collectDataForTest
import com.example.helper.getMockPagingData
import com.example.search.domain.model.User
import com.example.search.domain.model.toUser
import com.example.search.domain.usecase.SearchUserUseCase
import com.google.common.truth.Truth.assertThat
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.TestCoroutineScheduler
import kotlinx.coroutines.test.TestScope
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.ArgumentCaptor
import org.mockito.ArgumentMatchers.anyString
import org.mockito.Mockito
import org.mockito.Mockito.verify
import org.mockito.Mockito.verifyNoMoreInteractions

class SearchViewModelTest() {
    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    private val mockSearchUserUseCase: SearchUserUseCase = Mockito.mock(SearchUserUseCase::class.java)

    private val mockObserver: Observer<PagingData<User>> = mockk()
    private lateinit var viewModel: SearchViewModel

    private lateinit var argumentCaptor: ArgumentCaptor<PagingData<User>>
    private val scheduler = TestCoroutineScheduler()
    private val dispatcher = StandardTestDispatcher(scheduler)
    private val testCoroutineScope = TestScope(dispatcher)

    @Before
    fun setUp() {
        Dispatchers.setMain(dispatcher)
        viewModel = SearchViewModel(mockSearchUserUseCase)
        argumentCaptor = argumentCaptor()
    }

    @Test
    fun `search user returns paging data on success`() = runTest {
        val users = getMockSearchResponse("search_response.json")
            .items.map { it.toUser() }
        val pagingData = getMockPagingData(users)

        Mockito.`when`(mockSearchUserUseCase.searchUser(anyString()))
            .thenReturn(pagingData)

        viewModel.onSearchTextChanged("user")

        testCoroutineScope.launch {
            verify(mockSearchUserUseCase).searchUser("user")
            viewModel.pagingState.collectLatest {
                verify(mockObserver).onChanged(it)
            }

            viewModel.pagingState.collectLatest {
                val items = it.collectDataForTest()
                assertThat(items.size).isEqualTo(users.size)
                assertThat(items.first().id).isEqualTo(users.first().id)
            }
            verifyNoMoreInteractions(mockObserver)
        }
    }

    @Test
    fun `Searching with a keyword changes paging State flow data`() = runTest {
        Mockito.`when`(mockSearchUserUseCase.searchUser(anyString()))
            .thenReturn(getMockPagingData(listOf()))

        val query = "Cat"

        testCoroutineScope.launch {
            viewModel.onSearchTextChanged(query)

            viewModel.searchText.collectLatest {
                assertThat(it).isEqualTo(query)
            }

            viewModel.pagingState.collectLatest {
                verify(mockObserver).onChanged(it)
            }

            verifyNoMoreInteractions(mockObserver)
        }
    }
}
