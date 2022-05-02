package com.example.basichttp.model

import kotlinx.serialization.Serializable

@Serializable
data class TImage(
    val id:String,
    val url:String
)
