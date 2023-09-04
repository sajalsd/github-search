package com.example.search.presentation.ui

import androidx.compose.ui.test.junit4.ComposeContentTestRule
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.onRoot
import androidx.compose.ui.test.performClick
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.search.domain.model.User
import org.junit.Assert
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class UserListItemTest {
    @Rule
    @JvmField
    var composeTestRule: ComposeContentTestRule = createComposeRule()

    private val user = User(
        id = 1,
        avatarUrl = "",
        followingUrl = "",
        followersUrl = "",
        login = "username"
    )

    private fun setupContent(onUserClick: () -> Unit = {}) {
        composeTestRule.setContent {
            UserListItem(
                user = user,
                onUserClick = onUserClick
            )
        }
    }

    @Test
    fun userListItem_loads_userName() {
        setupContent()
        composeTestRule.onNodeWithText(user.login).assertExists()
    }

    @Test
    fun userListItem_loads_userImage() {
        setupContent()
        composeTestRule.onNodeWithContentDescription(user.login).assertExists()
    }

    @Test
    fun clickingOnUserListItem_rerunsUserClickCallback() {
        var clicked = false
        setupContent { clicked = true }

        composeTestRule.onRoot().performClick()
        Assert.assertTrue(clicked)
    }
}
