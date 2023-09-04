package com.example.search.presentation.ui

import androidx.compose.ui.test.assert
import androidx.compose.ui.test.assertCountEquals
import androidx.compose.ui.test.hasTestTag
import androidx.compose.ui.test.hasText
import androidx.compose.ui.test.junit4.ComposeContentTestRule
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onChildren
import androidx.compose.ui.test.onFirst
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.performScrollToNode
import androidx.paging.PagingData
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.search.domain.model.User
import com.example.search.presentation.utils.USER_LIST_TEST_TAG
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class UserListViewTest {
    @Rule
    @JvmField
    var composeTestRule: ComposeContentTestRule = createComposeRule()

    private fun setupScreen(users: List<User>) {
        val pagingData = flowOf(PagingData.from(users))
        composeTestRule.setContent {
            UserListView(
                lazyPagingUsers = pagingData.collectAsLazyPagingItems(),
                onSearchItemClick = {}
            )
        }
    }

    @Test
    fun emptyPagingData_shows_Empty_List() {
        setupScreen(emptyList())

        composeTestRule.onNodeWithTag(USER_LIST_TEST_TAG).assertExists()
        composeTestRule.onNodeWithTag(USER_LIST_TEST_TAG)
            .onChildren()
            .assertCountEquals(0)
    }

    @Test
    fun shows_right_data() = runTest {
        val userList = getUserData()
        setupScreen(userList)

        composeTestRule.onNodeWithTag(USER_LIST_TEST_TAG).assertExists()
        composeTestRule.onNodeWithTag(USER_LIST_TEST_TAG)
            .onChildren()
            .onFirst()
            .assert(hasTestTag(userList.first().id.toString()))
            .assert(hasText("username0"))
    }

    @Test
    fun shows_user_Lazyly() {
        val userList = getUserData()
        setupScreen(userList)

        val userListNode = composeTestRule.onNodeWithTag(USER_LIST_TEST_TAG)
        composeTestRule.onNodeWithTag(userList[7].id.toString()).assertDoesNotExist()

        userListNode.performScrollToNode(hasTestTag(userList[7].id.toString()))
        composeTestRule.onNodeWithTag(userList[7].id.toString()).assertExists()
    }

    @Test
    fun scrolling_shows_next_data() = runTest {
        val userList = getUserData()

        setupScreen(userList)

        val userListNode = composeTestRule.onNodeWithTag(USER_LIST_TEST_TAG)

        userListNode.assertExists()

        userListNode.performScrollToNode(hasTestTag(userList[7].id.toString()))
        composeTestRule.onNodeWithTag(userList[7].id.toString()).assertExists()

        userListNode.performScrollToNode(hasTestTag(userList.last().id.toString()))
        composeTestRule.onNodeWithTag(userList.last().id.toString()).assertExists()
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
