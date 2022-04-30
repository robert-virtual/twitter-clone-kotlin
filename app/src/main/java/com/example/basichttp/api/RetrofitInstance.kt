package com.example.basichttp.api

import com.example.basichttp.utils.Consts
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitInstance {
    private val retrofit = Retrofit.Builder()
        .baseUrl(Consts.TWITTER_API_BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
    val twitterApi:TwitterApi by lazy {
        retrofit.create(TwitterApi::class.java)
    }
}