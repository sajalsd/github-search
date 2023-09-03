package com.example.search.di

import com.example.search.data.repository.SearchUserRepository
import com.example.search.data.repository.SearchUserRepositoryImpl
import com.example.search.domain.usecase.SearchUserUseCase
import com.example.search.domain.usecase.SearchUserUseCaseImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class SearchModule {
    @Binds
    internal abstract fun bindSearchRepository(
        searchUserRepositoryImpl: SearchUserRepositoryImpl
    ): SearchUserRepository

    @Binds
    internal abstract fun bindSearchUserUseCase(
        searchUserUseCaseImpl: SearchUserUseCaseImpl
    ): SearchUserUseCase
}
