package com.example.search.domain.model

data class User(
    val id: Int,
    val login: String,
    val avatarUrl: String,
    val followersUrl: String,
    val followingUrl: String
)
