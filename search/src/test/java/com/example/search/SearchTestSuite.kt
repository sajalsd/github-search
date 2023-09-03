package com.example.search

import com.example.search.data.paging.SearchPagingSourceTest
import com.example.search.data.repository.SearchUserRepositoryTest
import com.example.search.domain.model.UserMapperKtTest
import com.example.search.domain.usecase.SearchUserUseCaseTest
import com.example.search.presentation.viewmodel.SearchViewModelTest
import org.junit.runner.RunWith
import org.junit.runners.Suite
import org.junit.runners.Suite.SuiteClasses

@RunWith(Suite::class)
@SuiteClasses(
    SearchPagingSourceTest::class,
    SearchUserRepositoryTest::class,
    UserMapperKtTest::class,
    SearchUserUseCaseTest::class,
    SearchViewModelTest::class
)
class SearchTestSuite
