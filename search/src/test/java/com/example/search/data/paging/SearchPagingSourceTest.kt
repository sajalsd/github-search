package com.example.search.data.paging

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.paging.PagingSource
import com.example.helper.ResponseUtil
import com.example.helper.argumentCaptor
import com.example.helper.capture
import com.example.network.data.dto.SearchResponse
import com.example.network.data.dto.UserDto
import com.example.network.data.model.ApiResponse
import com.example.search.data.repository.SearchUserRepository
import com.google.common.truth.Truth.assertThat
import io.mockk.MockKAnnotations
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.TestCoroutineScheduler
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule
import org.mockito.ArgumentMatchers.anyInt
import org.mockito.ArgumentMatchers.anyString
import org.mockito.Mockito
import org.mockito.Mockito.mock
import org.mockito.Mockito.`when`

class SearchPagingSourceTest() {
    @Rule
    @JvmField
    var rule: TestRule = InstantTaskExecutorRule()

    private val scheduler = TestCoroutineScheduler()
    private val dispatcher = StandardTestDispatcher(scheduler)

    private val searchUserRepository: SearchUserRepository = mock(SearchUserRepository::class.java)

    private lateinit var searchPagingSource: SearchPagingSource

    @Before
    fun setup() {
        MockKAnnotations.init(this, relaxUnitFun = true)
        Dispatchers.setMain(dispatcher)
        searchPagingSource = SearchPagingSource(searchUserRepository, "")
    }

    @Test
    fun `UserSearchPagingSource pass offset for first page correctly`() = runTest {
        val searchResponseMock = mock(SearchResponse::class.java)
        val userResponse = ApiResponse.Success(searchResponseMock)

        `when`(searchUserRepository.searchUser(anyString(), anyInt(), anyInt()))
            .thenReturn(userResponse)

        val offsetCaptor = argumentCaptor<Int>()

        val pagingParam = PagingSource.LoadParams.Refresh(1, 10, false)

        searchPagingSource.load(pagingParam)
        Mockito.verify(searchUserRepository).searchUser(
            anyString(),
            anyInt(),
            capture(offsetCaptor)
        )

        assertThat(offsetCaptor.value).isEqualTo(1)
    }

    @Test
    fun `UserSearchPagingSource pass offset page correctly`() = runTest {
        val searchResponseMock = mock(SearchResponse::class.java)
        val userResponse = ApiResponse.Success(searchResponseMock)

        `when`(searchUserRepository.searchUser(anyString(), anyInt(), anyInt()))
            .thenReturn(userResponse)

        val offsetCaptor = argumentCaptor<Int>()

        val pagingParam = PagingSource.LoadParams.Refresh(3, 20, false)

        searchPagingSource.load(pagingParam)
        Mockito.verify(searchUserRepository).searchUser(
            anyString(),
            capture(offsetCaptor),
            anyInt()
        )

        assertThat(offsetCaptor.value).isEqualTo(20)
    }

    @Throws(RuntimeException::class)
    @Test
    fun `UserSearchPagingSource loading failure`() = runTest {
        val exception = RuntimeException("404", Throwable())
        val apiResponse = ApiResponse.Error(exception)

        `when`(searchUserRepository.searchUser(anyString(), anyInt(), anyInt()))
            .thenReturn(apiResponse)

        val expectedResult = PagingSource.LoadResult.Error<Int, UserDto>(exception)

        val loadResult = searchPagingSource.load(
            PagingSource.LoadParams.Refresh(
                key = 1,
                loadSize = 10,
                placeholdersEnabled = false
            )
        )

        assertThat(expectedResult).isEqualTo(loadResult)
    }

    @Test
    fun `UserSearchPagingSource refresh load data`() = runTest {
        val userResponse = ResponseUtil.getSuccessResponse("search_response.json")

        `when`(searchUserRepository.searchUser(anyString(), anyInt(), anyInt()))
            .thenReturn(userResponse)

        val expectedResult = PagingSource.LoadResult.Page(
            data = userResponse.responseData.items,
            prevKey = null,
            nextKey = 2
        )

        val loadResult = searchPagingSource.load(
            PagingSource.LoadParams.Refresh(
                key = 1,
                loadSize = 10,
                placeholdersEnabled = false
            )
        )

        assertThat(loadResult).isEqualTo(expectedResult)
    }

    @Test
    fun `UserSearchPagingSource append success`() = runTest {
        val userResponse = ResponseUtil.getSuccessResponse("search_response.json")

        `when`(searchUserRepository.searchUser(anyString(), anyInt(), anyInt()))
            .thenReturn(userResponse)

        val expectedResult = PagingSource.LoadResult.Page(
            data = userResponse.responseData.items,
            prevKey = null,
            nextKey = 2
        )

        val loadResult = searchPagingSource.load(
            PagingSource.LoadParams.Append(
                key = 1,
                loadSize = 10,
                placeholdersEnabled = false
            )
        )

        assertThat(loadResult).isEqualTo(expectedResult)
    }

    @Test
    fun `UserSearchPagingSource prepend success`() = runTest {
        val userResponse = ResponseUtil.getSuccessResponse("search_response.json")

        `when`(searchUserRepository.searchUser(anyString(), anyInt(), anyInt()))
            .thenReturn(userResponse)

        val expectedResult = PagingSource.LoadResult.Page(
            data = userResponse.responseData.items,
            prevKey = 3,
            nextKey = 5
        )

        val loadResult = searchPagingSource.load(
            PagingSource.LoadParams.Prepend(
                key = 4,
                loadSize = 10,
                placeholdersEnabled = false
            )
        )

        assertThat(loadResult).isEqualTo(expectedResult)
    }

    @Test
    fun `UserSearchPagingSource end of paging`() = runTest {
        val userResponse = ResponseUtil.getSuccessResponse("search_empty_response.json")

        `when`(searchUserRepository.searchUser(anyString(), anyInt(), anyInt()))
            .thenReturn(userResponse)

        val expectedResult = PagingSource.LoadResult.Page(
            data = userResponse.responseData.items,
            prevKey = 798,
            nextKey = null
        )

        val loadResult = searchPagingSource.load(
            PagingSource.LoadParams.Prepend(
                key = 799,
                loadSize = 10,
                placeholdersEnabled = false
            )
        )

        assertThat(loadResult).isEqualTo(expectedResult)
    }
}
