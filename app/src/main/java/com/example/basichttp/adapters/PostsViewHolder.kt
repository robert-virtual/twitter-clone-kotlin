package com.example.basichttp.adapters

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.example.basichttp.databinding.PostItemBinding
import com.example.basichttp.model.Post

class PostsViewHolder (view:View):RecyclerView.ViewHolder(view){
    val binding = PostItemBinding.bind(view)
    fun render(post:Post){
       binding.user.text = post.user?.name
        binding.username.text = "@${post.user?.name}"
        binding.content.text = post.content

    }
}