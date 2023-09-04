package com.example.details.presentation.viewmodel

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.core.util.Resource
import com.example.details.domain.usecase.GetUserDetailsUseCase
import com.example.details.presentation.ui.model.UserDetailsState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UserDetailsViewModel @Inject constructor(
    private val getUserDetailsUseCase: GetUserDetailsUseCase
) : ViewModel() {

    private val _state = mutableStateOf(UserDetailsState())
    val state: State<UserDetailsState> = _state

    fun getUserDetails(userName: String) {
        viewModelScope.launch {
            fetchUserDetails(userName)
        }
    }

    private suspend fun fetchUserDetails(userName: String) {
        getUserDetailsUseCase.getUserDetails(userName)
            .onEach { result ->
                when (result) {
                    is Resource.Success -> {
                        _state.value = UserDetailsState(userDetails = result.data)
                    }

                    is Resource.Error -> {
                        _state.value = UserDetailsState(error = result.message)
                    }

                    is Resource.Loading -> {
                        _state.value = UserDetailsState(isLoading = true)
                    }
                }
            }
            .launchIn(viewModelScope)
    }
}
