package com.example.basichttp.model

import kotlinx.serialization.Serializable

@Serializable
data class Post(
    val content: String,
    val id: String? = null,
    val images: List<TImage>? = null,
    val user: User? = null,
    val userId: String? = null,
    val createdAt: String? = null
)