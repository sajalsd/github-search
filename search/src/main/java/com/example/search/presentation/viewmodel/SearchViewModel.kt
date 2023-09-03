package com.example.search.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.search.domain.model.User
import com.example.search.domain.usecase.SearchUserUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val searchUserUseCase: SearchUserUseCase
) : ViewModel() {

    private var _pagingState = MutableSharedFlow<PagingData<User>>()
    val pagingState: Flow<PagingData<User>> = _pagingState

    private val _searchText = MutableStateFlow("")
    val searchText = _searchText.asStateFlow()

    @OptIn(FlowPreview::class)
    private val _searchUserFlow: StateFlow<String> = _searchText
        .debounce(1000)
        .distinctUntilChanged()
        .filter { it.trim().isNotEmpty() }
        .stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(2000),
            _searchText.value
        )

    init {
        viewModelScope.launch {
            _searchUserFlow.collectLatest {
                if (it.isNotEmpty()) searchUser(it)
            }
        }
    }

    fun onSearchTextChanged(text: String) {
        _searchText.value = text
    }

    fun onTextClear() {
        _searchText.value = ""
    }

    private suspend fun searchUser(text: String) {
        searchUserUseCase.searchUser(text)
            .cachedIn(viewModelScope)
            .collectLatest {
                _pagingState.emit(it)
            }
    }
}
