package com.example.basichttp.api

import com.example.basichttp.utils.Consts
import io.ktor.client.*
import io.ktor.client.engine.cio.*
import io.ktor.client.plugins.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.http.*
import io.ktor.serialization.kotlinx.json.*

object TwitterKtor {
    val client = HttpClient(CIO){
        defaultRequest {
            host = "twitter-z.herokuapp.com"
            url{
                protocol = URLProtocol.HTTPS
            }
        }
        install(ContentNegotiation){
            json()
        }
    }
}