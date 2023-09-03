package com.example.network.data.dto

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class UserDetailsDto(
    @Json(name = "avatar_url")
    val avatarUrl: String,
    @Json(name = "bio")
    val bio: String?,
    @Json(name = "company")
    val company: String?,
    @Json(name = "email")
    val email: String?,
    @Json(name = "followers")
    val followers: Int,
    @Json(name = "following")
    val following: Int,
    @Json(name = "hireable")
    val hireable: Boolean?,
    @Json(name = "html_url")
    val htmlUrl: String,
    @Json(name = "id")
    val id: Int,
    @Json(name = "location")
    val location: String?,
    @Json(name = "login")
    val login: String,
    @Json(name = "name")
    val name: String?,
    @Json(name = "node_id")
    val nodeId: String,
    @Json(name = "organizations_url")
    val organizationsUrl: String,
    @Json(name = "repos_url")
    val reposUrl: String,
    @Json(name = "site_admin")
    val siteAdmin: Boolean,
    @Json(name = "type")
    val type: String,
    @Json(name = "updated_at")
    val updatedAt: String,
    @Json(name = "url")
    val url: String
)
