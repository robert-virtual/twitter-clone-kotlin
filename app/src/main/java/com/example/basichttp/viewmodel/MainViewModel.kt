package com.example.basichttp.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.basichttp.api.RetrofitInstance
import com.example.basichttp.model.Post
import kotlinx.coroutines.launch
import java.lang.Exception



class MainViewModel:ViewModel() {
    val posts = MutableLiveData<List<Post>>()
    val error = MutableLiveData<String>()

    fun getPosts(){
        viewModelScope.launch {
            try {
                posts.value = RetrofitInstance.twitterApi.getPosts()
            }catch (e:Exception){
              error.value = e.message
            }
        }
    }

}