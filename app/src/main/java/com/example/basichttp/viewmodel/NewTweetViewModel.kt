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
    val images = MutableLiveData<MutableList<MyImage>>()
    val selectedImages = MutableLiveData<MutableList<MyImage>>()

}