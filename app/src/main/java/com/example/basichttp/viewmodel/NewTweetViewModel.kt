package com.example.basichttp.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.basichttp.api.RetrofitInstance
import com.example.basichttp.model.MyImage
import com.example.basichttp.model.Post
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


    fun createPost(_post: Post){
       viewModelScope.launch {
           try {
               post.value = RetrofitInstance.twitterApi.createPost(_post)
           }catch (e:Exception){
              error.value = e.message
           }
       }
    }

    fun insertPost(_post: Post){
        viewModelScope.launch {
            try {
                val requestBody = RequestBody.create(MultipartBody.FORM,_post.content)

                val file = File(selectedImages[0].uri?.path!!)
                val content = MultipartBody.Part.createFormData("content",_post.content)
                val imagesBody = RequestBody.create(MultipartBody.FORM,file)
                val imagePart = MultipartBody.Part.createFormData("image",file.name,imagesBody)

                post.value = RetrofitInstance.twitterApi.insertPost(requestBody,imagePart)
            }catch (e:Exception){
                error.value = e.message
            }
        }
    }
}