package com.example.basichttp.api

import com.example.basichttp.model.Post
import retrofit2.http.GET

interface TwitterApi {
    @GET("/posts")
    suspend fun getPosts():List<Post>
}