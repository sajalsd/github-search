package com.example.search.presentation.ui

import android.content.Context
import android.view.inputmethod.InputMethodManager
import androidx.annotation.StringRes
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.compose.ui.test.assertCountEquals
import androidx.compose.ui.test.filter
import androidx.compose.ui.test.hasImeAction
import androidx.compose.ui.test.hasText
import androidx.compose.ui.test.junit4.ComposeContentTestRule
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onChildren
import androidx.compose.ui.test.onFirst
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performTextInput
import androidx.compose.ui.test.requestFocus
import androidx.compose.ui.text.input.ImeAction
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import com.example.search.R
import com.example.search.domain.model.User
import com.example.search.domain.usecase.FakeSearchUseCase
import com.example.search.presentation.utils.CIRCULAR_PROGRESS_TEST_TAG
import com.example.search.presentation.utils.SEARCH_BAR_TEST_TAG
import com.example.search.presentation.utils.USER_LIST_TEST_TAG
import com.example.search.presentation.viewmodel.SearchViewModel
import org.junit.Assert
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class SearchScreenTest {
    @Rule
    @JvmField
    var composeTestRule: ComposeContentTestRule = createComposeRule()

    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    val viewModel = SearchViewModel(FakeSearchUseCase())
    private fun setupScreen(
        searchViewModel: SearchViewModel = viewModel,
        onSearchItemClick: (User) -> Unit = {},
        onSearchClear: () -> Unit = {
            searchViewModel.onTextClear()
        }
    ) {
        composeTestRule.setContent {
            SearchScreen(
                searchViewModel = searchViewModel,
                onSearchItemClick = onSearchItemClick,
                onSearchClear = onSearchClear
            )
        }
    }

    @Test
    fun initialState_show_searchInput() {
        setupScreen()

        composeTestRule.onNodeWithTag(SEARCH_BAR_TEST_TAG).assertExists()
        composeTestRule.onNodeWithContentDescription(getString(R.string.search_bar_clear_des))
            .assertDoesNotExist()
        composeTestRule.onNodeWithText(getString(R.string.search_placeholder))
            .assertExists()

        Assert.assertTrue(isKeyboardShown().not())
    }

    @Test
    fun initialState_shows_search_hint() {
        setupScreen()
        composeTestRule.onNodeWithTag(SEARCH_BAR_TEST_TAG).assertExists()
        composeTestRule.onNodeWithText(getString(R.string.search_hint_description))
            .assertExists()
    }

    @Test
    fun initialState_clearButton_hidden() {
        setupScreen()
        composeTestRule.onNodeWithTag(SEARCH_BAR_TEST_TAG).assertExists()
        composeTestRule.onNodeWithContentDescription(getString(R.string.search_bar_clear_des)).assertDoesNotExist()
    }

    @Test
    fun show_clear_while_typing() {
        setupScreen()
        composeTestRule.onNodeWithTag(SEARCH_BAR_TEST_TAG).assertExists()

        composeTestRule.onNodeWithTag(SEARCH_BAR_TEST_TAG)
            .onChildren()
            .filter(hasImeAction(ImeAction.Search))
            .onFirst()
            .requestFocus()
            .performTextInput("hello")

        composeTestRule.onNodeWithContentDescription(getString(R.string.search_bar_clear_des)).assertExists()
    }

    @Test
    fun clear_button_clear_text() {
        setupScreen()
        composeTestRule.onNodeWithTag(SEARCH_BAR_TEST_TAG).assertExists()

        composeTestRule.onNodeWithTag(SEARCH_BAR_TEST_TAG)
            .onChildren()
            .filter(hasImeAction(ImeAction.Search))
            .onFirst()
            .requestFocus()
            .performTextInput("hello")

        val clearNode = composeTestRule.onNodeWithContentDescription(getString(R.string.search_bar_clear_des))
        clearNode.assertExists()
        clearNode.performClick()

        composeTestRule.onNodeWithTag(SEARCH_BAR_TEST_TAG)
            .onChildren()
            .filter(hasImeAction(ImeAction.Search))
            .onFirst()

        composeTestRule.onNodeWithTag(SEARCH_BAR_TEST_TAG)
            .onChildren()
            .filter(hasText("hello"))
            .assertCountEquals(0)
    }

    @Test
    fun searching_shows_searchResult_hideHint() {
        setupScreen()

        composeTestRule.onNodeWithTag(SEARCH_BAR_TEST_TAG)
            .onChildren()
            .filter(hasImeAction(ImeAction.Search))
            .onFirst()
            .requestFocus()
            .performTextInput("hello")

        composeTestRule.waitForIdle()

        composeTestRule.onNodeWithTag(SEARCH_BAR_TEST_TAG).assertExists()

        composeTestRule.waitForIdle()

        composeTestRule.onNodeWithText(getString(R.string.search_hint_description))
            .assertDoesNotExist()

        composeTestRule.mainClock.advanceTimeBy(3 * 1000)
        composeTestRule.waitForIdle()

        composeTestRule.onNodeWithText(getString(R.string.search_hint_description))
            .assertDoesNotExist()

        composeTestRule.onNodeWithTag(CIRCULAR_PROGRESS_TEST_TAG).assertExists()
        composeTestRule.onNodeWithTag(USER_LIST_TEST_TAG).assertExists()
    }

    private fun getString(@StringRes resId: Int): String {
        val context = InstrumentationRegistry.getInstrumentation().targetContext
        return context.resources.getString(resId)
    }

    private fun isKeyboardShown(): Boolean {
        val inputMethodManager = InstrumentationRegistry.getInstrumentation()
            .targetContext.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        return inputMethodManager.isAcceptingText
    }
}
