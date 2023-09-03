package com.example.network.data.dto

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class UserDto(
    @Json(name = "avatar_url")
    val avatarUrl: String,
    @Json(name = "followers_url")
    val followersUrl: String,
    @Json(name = "following_url")
    val followingUrl: String,
    @Json(name = "gists_url")
    val gistsUrl: String,
    @Json(name = "html_url")
    val htmlUrl: String,
    @Json(name = "id")
    val id: Int,
    @Json(name = "login")
    val login: String,
    @Json(name = "organizations_url")
    val organizationsUrl: String,
    @Json(name = "repos_url")
    val reposUrl: String,
    @Json(name = "type")
    val type: String,
    @Json(name = "url")
    val url: String
)
