package com.example.search.data.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.network.data.dto.UserDto
import com.example.network.data.model.ApiResponse
import com.example.search.domain.repository.SearchUserRepository
import javax.inject.Inject

class SearchPagingSource @Inject constructor(
    private val searchUserRepository: SearchUserRepository,
    private val query: String
) : PagingSource<Int, UserDto>() {
    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, UserDto> {
        val currentPage = params.key ?: 1
        val result = searchUserRepository.searchUser(query, params.loadSize, currentPage)

        return try {
            return when (result) {
                is ApiResponse.Success -> {
                    val endOfPaginationReached = result.responseData.items.isEmpty()

                    val userList = result.responseData.items

                    LoadResult.Page(
                        data = userList,
                        prevKey = if (currentPage <= 1) null else currentPage - 1,
                        nextKey = if (endOfPaginationReached) null else currentPage + 1
                    )
                }

                is ApiResponse.Error -> {
                    LoadResult.Error(result.exception)
                }
            }
        } catch (exception: Exception) {
            LoadResult.Error(exception)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, UserDto>): Int? {
        return state.anchorPosition
    }
}
