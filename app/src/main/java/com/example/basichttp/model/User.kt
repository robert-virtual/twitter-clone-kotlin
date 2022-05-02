package com.example.basichttp.model

import kotlinx.serialization.Serializable

@Serializable
data class User(
    val email: String,
    val id: String,
    val name: String,
    val password: String?
)