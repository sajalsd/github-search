package com.example.details

import com.example.details.data.repository.UserDetailsRepositoryImplTest
import com.example.details.domain.model.UserDetailsMapperKtTest
import com.example.details.domain.usecase.GetUserDetailsUseCaseImplTest
import com.example.details.presentation.viewmodel.UserDetailsViewModelTest
import org.junit.runner.RunWith
import org.junit.runners.Suite
import org.junit.runners.Suite.SuiteClasses

@RunWith(Suite::class)
@SuiteClasses(
    UserDetailsRepositoryImplTest::class,
    UserDetailsMapperKtTest::class,
    GetUserDetailsUseCaseImplTest::class,
    UserDetailsViewModelTest::class
)
class DetailsTestSuite
