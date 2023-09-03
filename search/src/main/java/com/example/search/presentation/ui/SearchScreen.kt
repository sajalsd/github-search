package com.example.search.presentation.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.paging.LoadState
import androidx.paging.compose.collectAsLazyPagingItems
import com.example.core.ui.EmptyView
import com.example.network.data.model.ConnectionException
import com.example.search.R
import com.example.search.domain.model.User
import com.example.search.presentation.viewmodel.SearchViewModel

@Composable
fun SearchScreen(
    searchViewModel: SearchViewModel = hiltViewModel(),
    onSearchItemClick: (User) -> Unit,
    onSearchClear: () -> Unit
) {
    val searchText by searchViewModel.searchText.collectAsState("")
    val lazyPagingUsers = searchViewModel.pagingState.collectAsLazyPagingItems()
    val focusManager = LocalFocusManager.current

    Column {
        SearchBarView(
            placeholder = stringResource(id = R.string.search_placeholder),
            searchText = searchText,
            onSearchTextChange = searchViewModel::onSearchTextChanged,
            onClearClick = {
                onSearchClear.invoke()
                searchViewModel.onTextClear()
            },
            focusManager = focusManager
        )

        Box(modifier = Modifier.fillMaxSize()) {
            when {
                searchText.isEmpty() -> SearchHintView()

                (lazyPagingUsers.loadState.refresh is LoadState.Loading) &&
                    searchText.isNotEmpty() -> CircularProgressIndicator(Modifier.align(Alignment.Center))

                lazyPagingUsers.loadState.refresh is LoadState.Error -> {
                    val exception = (lazyPagingUsers.loadState.refresh as LoadState.Error).error
                    exception.printStackTrace()
                    when (exception) {
                        is ConnectionException -> NoInternetView()
                        else -> EmptyView(text = exception.localizedMessage ?: "")
                    }
                }

                lazyPagingUsers.itemCount == 0 -> NoSearchResultFoundView()

                else -> UserListView(
                    lazyPagingUsers = lazyPagingUsers,
                    onSearchItemClick = {
                        onSearchItemClick(it)
                        focusManager.clearFocus()
                    }
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun SearchScreenPreview() {
    return SearchScreen(onSearchClear = {}, onSearchItemClick = {})
}
