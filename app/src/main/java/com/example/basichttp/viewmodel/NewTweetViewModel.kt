package com.example.basichttp.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.basichttp.api.RetrofitInstance
import com.example.basichttp.api.TwitterKtor
import com.example.basichttp.model.MyImage
import com.example.basichttp.model.Post
import io.ktor.client.call.*
import io.ktor.client.request.*
import io.ktor.client.request.forms.*
import io.ktor.http.*
import kotlinx.coroutines.launch
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import java.io.File
import java.lang.Exception


class NewTweetViewModel:ViewModel() {
   val post = MutableLiveData<Post>()
    val error = MutableLiveData<String>()
    val images = mutableListOf<MyImage>()
    val selectedImages = mutableListOf<MyImage>()


    fun createPost(content: String){
       viewModelScope.launch {
           try {
               post.value = TwitterKtor.client.submitFormWithBinaryData(
                   url ="https://twitter-z.herokuapp.com/posts",
               formData = formData {
                   append("content",content)
                   for (image in selectedImages){
                       append("images",image.byteArray!!,Headers.build {
                           append(HttpHeaders.ContentType,image.mimeType!!)
                           append(HttpHeaders.ContentDisposition,"filename=${image.name}")
                       })
                   }
               }
               ).body()

           }catch (e:Exception){
              error.value = e.message
           }
       }
    }

}