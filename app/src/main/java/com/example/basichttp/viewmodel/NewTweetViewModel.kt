package com.example.basichttp.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.basichttp.api.RetrofitInstance
import com.example.basichttp.model.MyImage
import com.example.basichttp.model.Post
import kotlinx.coroutines.launch
import java.lang.Exception


class NewTweetViewModel:ViewModel() {
   val post = MutableLiveData<Post>()
    val error = MutableLiveData<String>()
    val images = mutableListOf<MyImage>()
    val selectedImages = mutableListOf<MyImage>()


    fun createPost(_post: Post){
       viewModelScope.launch {
           try {
               post.value = RetrofitInstance.twitterApi.createPost(_post)
           }catch (e:Exception){
              error.value = e.message
           }
       }
    }
}