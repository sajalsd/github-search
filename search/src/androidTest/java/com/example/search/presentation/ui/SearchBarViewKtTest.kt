package com.example.search.presentation.ui

import android.content.Context
import android.view.inputmethod.InputMethodManager
import androidx.annotation.StringRes
import androidx.compose.ui.test.filter
import androidx.compose.ui.test.hasImeAction
import androidx.compose.ui.test.junit4.ComposeContentTestRule
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onChildren
import androidx.compose.ui.test.onFirst
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performTextInput
import androidx.compose.ui.test.requestFocus
import androidx.compose.ui.text.input.ImeAction
import androidx.test.platform.app.InstrumentationRegistry
import com.example.search.R
import com.example.search.presentation.utils.SEARCH_BAR_TEST_TAG
import com.example.search.presentation.utils.SEARCH_BAR_VIEW_TEST_TAG
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class SearchBarViewKtTest {
    @Rule
    @JvmField
    var composeTestRule: ComposeContentTestRule = createComposeRule()

    private val placeHolder = "placeholder"

    @Before
    fun setup() {
        composeTestRule.setContent {
            SearchBarView(
                placeholder = placeHolder,
                searchText = "",
                onSearchTextChange = {},
                onClearClick = {}
            )
        }
    }

    @Test
    fun searchBarIsShown() {
        composeTestRule.onNodeWithTag(SEARCH_BAR_VIEW_TEST_TAG).assertExists()
        composeTestRule.onNodeWithTag(SEARCH_BAR_TEST_TAG).assertExists()
    }

    @Test
    fun searchBarIsShown_KeyboardHidden_onFirst() {
        composeTestRule.onNodeWithTag(SEARCH_BAR_VIEW_TEST_TAG).assertExists()

        Assert.assertTrue(isKeyboardShown().not())
    }

    @Test
    fun searchBarIsShown_PlaceholderShown(): Unit = with(composeTestRule) {
        onNodeWithTag(SEARCH_BAR_VIEW_TEST_TAG).assertExists()
        onNodeWithTag(SEARCH_BAR_TEST_TAG).assertExists()

        onNodeWithText(placeHolder).assertExists()
    }

    @Test
    fun searchBarIsShown_ClearButtonHidden() {
        composeTestRule.onNodeWithContentDescription(getString(R.string.search_bar_clear_des))
            .assertDoesNotExist()
    }

    @Test
    fun searchBarIsShown_KeyboardAndClearButtonShownWhenFocused() {
        val clearButtonNode = composeTestRule.onNodeWithContentDescription(getString(R.string.search_bar_clear_des))

        clearButtonNode.assertDoesNotExist()

        composeTestRule.onNodeWithTag(SEARCH_BAR_TEST_TAG)
            .onChildren()
            .filter(hasImeAction(ImeAction.Search))
            .onFirst()
            .requestFocus()
            .performTextInput("hello")

        clearButtonNode.assertExists()
        Assert.assertTrue(isKeyboardShown())
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
