package com.example.search.domain.usecase

import androidx.paging.PagingData
import com.example.search.domain.model.User
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf

class FakeSearchUseCase() : SearchUserUseCase {
    override suspend fun searchUser(query: String): Flow<PagingData<User>> {
        return flowOf(PagingData.from(getUserData()))
    }

    private fun getUserData(): List<User> {
        val userList = mutableListOf<User>()
        for (i in 0..15) {
            userList.add(
                User(
                    id = i,
                    avatarUrl = "",
                    followingUrl = "",
                    followersUrl = "",
                    login = "username$i"
                )
            )
        }
        return userList
    }
}
