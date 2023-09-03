package com.example.githubsearch.ui

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.example.search.presentation.ui.SearchScreen

@Composable
fun CompleteSearchScreen(modifier: Modifier = Modifier) {
    Surface(modifier = modifier.fillMaxSize()) {
        SearchScreen(
            onSearchItemClick = {
            },
            onSearchClear = {
            }
        )
    }
}
