package com.example

import com.example.details.presentation.ui.UserDetailsScreenTest
import com.example.search.presentation.ui.SearchBarViewTest
import com.example.search.presentation.ui.SearchScreenTest
import com.example.search.presentation.ui.UserListItemTest
import com.example.search.presentation.ui.UserListViewTest
import org.junit.runner.RunWith
import org.junit.runners.Suite

@RunWith(Suite::class)
@Suite.SuiteClasses(
    SearchBarViewTest::class,
    UserListItemTest::class,
    UserListViewTest::class,
    SearchScreenTest::class,
    UserDetailsScreenTest::class
)
class UiTestSuite
