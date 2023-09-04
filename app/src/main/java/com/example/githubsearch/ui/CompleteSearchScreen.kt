package com.example.githubsearch.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.BottomSheetScaffold
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.SheetValue
import androidx.compose.material3.Surface
import androidx.compose.material3.rememberBottomSheetScaffoldState
import androidx.compose.material3.rememberStandardBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.details.presentation.ui.UserDetailsScreen
import com.example.details.presentation.viewmodel.UserDetailsViewModel
import com.example.search.presentation.ui.SearchScreen
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CompleteSearchScreen(modifier: Modifier = Modifier) {
    Surface(modifier = modifier.fillMaxSize()) {
        val bottomSheetState = rememberStandardBottomSheetState(
            initialValue = SheetValue.Hidden,
            skipHiddenState = false
        )
        val searchDetailsViewModel: UserDetailsViewModel = hiltViewModel()

        val scaffoldState = rememberBottomSheetScaffoldState(
            bottomSheetState = bottomSheetState
        )
        val scope = rememberCoroutineScope()

        BottomSheetScaffold(
            scaffoldState = scaffoldState,
            sheetContent = {
                UserDetailsScreen()
            },
            sheetShadowElevation = 16.dp,
            sheetPeekHeight = 0.dp
        ) {
            Box(
                modifier = Modifier
                    .padding(it)
                    .fillMaxSize()
                    .padding(horizontal = dimensionResource(com.example.core.R.dimen.padding))
            ) {
                SearchScreen(
                    onSearchItemClick = {
                        scope.launch {
                            searchDetailsViewModel.getUserDetails(it.login)
                            if (bottomSheetState.isVisible.not()) bottomSheetState.expand()
                        }
                    },
                    onSearchClear = {
                        scope.launch {
                            if (bottomSheetState.isVisible) bottomSheetState.hide()
                        }
                    }
                )
            }
        }
    }
}
