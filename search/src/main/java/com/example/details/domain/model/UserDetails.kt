package com.example.details.domain.model

data class UserDetails(
    val name: String,
    val userName: String,
    val avatarUrl: String,
    val bio: String,
    val email: String,
    val followers: Int,
    val following: Int,
    val location: String
)
