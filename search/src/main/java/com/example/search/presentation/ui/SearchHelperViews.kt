package com.example.search.presentation.ui

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.example.core.ui.EmptyView
import com.example.search.R

@Composable
fun NoSearchResultFoundView() {
    EmptyView(text = stringResource(id = R.string.search_no_result_found))
}

@Composable
fun NoInternetView() {
    EmptyView(text = stringResource(id = R.string.search_no_internet))
}

@Composable
fun SearchHintView() {
    EmptyView(
        imageVector = Icons.Filled.Search,
        imageContentDescription = stringResource(id = R.string.search_hint_image_description),
        text = stringResource(id = R.string.search_hint_description)
    )
}
