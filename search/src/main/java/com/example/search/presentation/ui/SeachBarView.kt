package com.example.search.presentation.ui

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.SearchBar
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusManager
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.tooling.preview.Preview
import com.example.core.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchBarView(
    modifier: Modifier = Modifier,
    placeholder: String,
    searchText: String,
    onSearchTextChange: (String) -> Unit,
    onClearClick: () -> Unit,
    focusManager: FocusManager? = null
) {
    var showClearButton by remember { mutableStateOf(false) }
    val active by remember { mutableStateOf(false) }
    val keyboardController = LocalSoftwareKeyboardController.current

    return Box(modifier = modifier) {
        SearchBar(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = dimensionResource(id = R.dimen.small_padding))
                .onFocusChanged { focusState ->
                    showClearButton = (focusState.isFocused)
                },

            query = searchText,
            onQueryChange = onSearchTextChange,

            leadingIcon = { Icon(imageVector = Icons.Filled.Search, contentDescription = "") },
            trailingIcon = {
                AnimatedVisibility(
                    visible = showClearButton,
                    enter = fadeIn(),
                    exit = fadeOut()
                ) {
                    IconButton(onClick = {
                        if (searchText.isEmpty()) {
                            keyboardController?.hide()
                            focusManager?.clearFocus()
                        } else {
                            onClearClick()
                        }
                    }) {
                        Icon(imageVector = Icons.Filled.Clear, contentDescription = "")
                    }
                }
            },
            placeholder = { Text(text = placeholder) },
            onSearch = {
                focusManager?.clearFocus()
            },
            onActiveChange = {},
            active = active,
            content = {}
        )
    }
}

@Preview(showBackground = true)
@Composable
fun SearchWidgetPreview() {
    return SearchBarView(
        placeholder = "Enter search keyword",
        searchText = "",
        onSearchTextChange = {},
        onClearClick = {}
    )
}
