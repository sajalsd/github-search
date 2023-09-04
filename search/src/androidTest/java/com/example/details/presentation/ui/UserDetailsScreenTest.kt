package com.example.details.presentation.ui

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.ComposeContentTestRule
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.details.domain.usecase.FakeGetUserDetailsUseCase
import com.example.details.domain.usecase.ResponseType
import com.example.details.presentation.ui.utils.CIRCULAR_PROGRESS_TEST_TAG
import com.example.details.presentation.ui.utils.EMPTY_VIEW_TEST_TAG
import com.example.details.presentation.ui.utils.USER_DETAILS_TEST_TAG
import com.example.details.presentation.viewmodel.UserDetailsViewModel
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class UserDetailsScreenTest {
    @Rule
    @JvmField
    var composeTestRule: ComposeContentTestRule = createComposeRule()

    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    private val useCase = FakeGetUserDetailsUseCase()
    private val userDetailsViewModel = UserDetailsViewModel(useCase)

    @Before
    fun setup() {
        composeTestRule.setContent {
            UserDetailsScreen(userDetailsViewModel)
        }
    }

    @Test
    fun userDetailsScreen_shows_loading() {
        useCase.setResponseType(ResponseType.LOADING)
        userDetailsViewModel.getUserDetails("user")

        composeTestRule.onNodeWithTag(CIRCULAR_PROGRESS_TEST_TAG).assertExists().assertIsDisplayed()

        composeTestRule.onNodeWithTag(EMPTY_VIEW_TEST_TAG).assertDoesNotExist()
        composeTestRule.onNodeWithTag(USER_DETAILS_TEST_TAG).assertDoesNotExist()
    }

    @Test
    fun userDetailsScreen_shows_error() {
        useCase.setResponseType(ResponseType.ERROR)
        userDetailsViewModel.getUserDetails("user")

        composeTestRule.onNodeWithTag(EMPTY_VIEW_TEST_TAG).assertExists().assertIsDisplayed()

        composeTestRule.onNodeWithTag(CIRCULAR_PROGRESS_TEST_TAG).assertDoesNotExist()
        composeTestRule.onNodeWithTag(USER_DETAILS_TEST_TAG).assertDoesNotExist()
    }

    @Test
    fun userDetailsScreen_shows_user_details() {
        useCase.setResponseType(ResponseType.SUCCESS)
        userDetailsViewModel.getUserDetails("user")

        composeTestRule.onNodeWithTag(CIRCULAR_PROGRESS_TEST_TAG).assertDoesNotExist()
        composeTestRule.onNodeWithTag(EMPTY_VIEW_TEST_TAG).assertDoesNotExist()

        composeTestRule.onNodeWithTag(USER_DETAILS_TEST_TAG).assertExists().assertIsDisplayed()
    }
}
