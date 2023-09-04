package com.example.search.presentation.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.unit.dp
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import com.example.search.domain.model.User
import com.example.search.presentation.utils.USER_LIST_TEST_TAG

@Composable
fun UserListView(
    lazyPagingUsers: LazyPagingItems<User>,
    onSearchItemClick: (User) -> Unit
) {
    LazyColumn(
        modifier = Modifier.fillMaxSize().testTag(USER_LIST_TEST_TAG),
        verticalArrangement = Arrangement.spacedBy(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        item {
            if (lazyPagingUsers.loadState.prepend is LoadState.Loading) {
                CircularProgressIndicator()
            }
        }

        items(lazyPagingUsers.itemCount) {
            lazyPagingUsers[it]?.let { user ->
                UserListItem(user) { onSearchItemClick(user) }
            }
        }

        item {
            if (lazyPagingUsers.loadState.append is LoadState.Loading) {
                CircularProgressIndicator()
            }
        }
    }
}
