package com.example.basichttp.model

data class Post(
    val content: String,
    val id: String? = null,
    val images: List<TImage>? = null,
    val user: User? = null,
    val userId: String? = null
)