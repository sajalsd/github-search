package com.example.details.di

import com.example.details.data.repository.UserDetailsRepository
import com.example.details.data.repository.UserDetailsRepositoryImpl
import com.example.details.domain.usecase.GetUserDetailsUseCase
import com.example.details.domain.usecase.GetUserDetailsUseCaseImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class UserDetailsModule {
    @Binds
    internal abstract fun bindUserDetailsRepository(
        userDetailsRepositoryImpl: UserDetailsRepositoryImpl
    ): UserDetailsRepository

    @Binds
    internal abstract fun bindGetUserDetailsUseCase(
        getUserDetailsUseCaseImpl: GetUserDetailsUseCaseImpl
    ): GetUserDetailsUseCase
}
