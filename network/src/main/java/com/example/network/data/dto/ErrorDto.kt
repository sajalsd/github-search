package com.example.network.model

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class ErrorDto(
    val code: String,
    @Json(name = "field")
    val errorField: String,
    val resource: String
)
