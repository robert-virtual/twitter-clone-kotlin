package com.example.basichttp.model

data class Post(
    val content: String,
    val id: String,
    val images: List<Any>,
    val user: User,
    val userId: String
)