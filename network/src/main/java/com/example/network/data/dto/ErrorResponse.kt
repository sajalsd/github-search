package com.example.network.data.dto

import com.example.network.model.ErrorDto
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class ErrorResponse(
    @Json(name = "documentation_url")
    val documentationUrl: String,
    val errors: List<ErrorDto>? = emptyList(),
    val message: String
)
