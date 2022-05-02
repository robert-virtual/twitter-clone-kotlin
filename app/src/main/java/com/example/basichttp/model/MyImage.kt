package com.example.basichttp.model

import android.net.Uri

data class MyImage (
    val uri:Uri? = null,
    val delete:Boolean = false,
    val url:String? = null,
    val span:Boolean = false
)