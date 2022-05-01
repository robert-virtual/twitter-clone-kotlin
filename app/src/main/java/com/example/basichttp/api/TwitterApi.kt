package com.example.basichttp.api

import com.example.basichttp.model.Post
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface TwitterApi {
    @GET("/posts")
    suspend fun getPosts():List<Post>

    @POST("/posts")
    suspend fun createPost(@Body post:Post):Post
}