package com.example.search.domain.model

import com.example.network.data.dto.UserDto

fun UserDto.toUser() = User(
    id,
    login,
    avatarUrl,
    followersUrl,
    followingUrl
)
