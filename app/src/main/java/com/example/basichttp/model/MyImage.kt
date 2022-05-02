package com.example.basichttp.model

import android.net.Uri

data class MyImage (
    val uri:Uri? = null,
    val delete:Boolean = false,
    val url:String? = null,
    val span:Boolean = false,
    val byteArray: ByteArray? = null,
    val name:String? = null,
    val mimeType:String? = null
)