package com.example.basichttp.api

import com.example.basichttp.model.Post
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.http.*

interface TwitterApi {
    @GET("/posts")
    suspend fun getPosts():List<Post>

    @POST("/posts")
    suspend fun createPost(@Body post:Post):Post

    @Multipart
    @POST("/posts")
    suspend fun insertPost(@Part("content") content:RequestBody,@Part image:MultipartBody.Part):Post
}