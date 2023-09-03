package com.example.details.domain.model

import com.example.network.data.dto.UserDetailsDto

fun UserDetailsDto.toUserDetails(): UserDetails =
    UserDetails(
        name = name ?: "",
        userName = login,
        avatarUrl = avatarUrl ?: "",
        bio = bio ?: "",
        email = email ?: "",
        followers = followers,
        following = following,
        location = location ?: ""
    )
