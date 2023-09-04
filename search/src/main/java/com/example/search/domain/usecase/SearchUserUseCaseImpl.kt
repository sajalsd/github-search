package com.example.search.domain.usecase

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.map
import com.example.search.data.paging.SearchPagingSource
import com.example.search.domain.model.User
import com.example.search.domain.model.toUser
import com.example.search.domain.repository.SearchUserRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class SearchUserUseCaseImpl @Inject constructor(
    private val searchUserRepository: SearchUserRepository
) : SearchUserUseCase {
    override suspend fun searchUser(query: String): Flow<PagingData<User>> {
        return Pager(
            config = PagingConfig(
                pageSize = PAGE_SIZE,
                prefetchDistance = PREFETCH_DISTANCE,
                initialLoadSize = INITIAL_LOAD_SIZE
            ),
            pagingSourceFactory = { SearchPagingSource(searchUserRepository, query) }
        ).flow.map { pagingData ->
            pagingData.map {
                it.toUser()
            }
        }
    }
    companion object {
        const val PAGE_SIZE = 20
        const val PREFETCH_DISTANCE = 10
        const val INITIAL_LOAD_SIZE = 40
    }
}
