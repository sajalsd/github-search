package com.example.details.presentation.ui.model

import com.example.core.util.UiText
import com.example.details.domain.model.UserDetails

data class UserDetailsState(
    val isLoading: Boolean = false,
    val userDetails: UserDetails? = null,
    val error: UiText? = null
)
