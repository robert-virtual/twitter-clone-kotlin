package com.example.basichttp.adapters

import android.view.View
import android.widget.Toast
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.basichttp.databinding.PostItemBinding
import com.example.basichttp.model.MyImage
import com.example.basichttp.model.Post
import com.example.basichttp.utils.Consts
import java.lang.Exception

class PostsViewHolder (view:View):RecyclerView.ViewHolder(view){
    val binding = PostItemBinding.bind(view)
    fun render(post:Post){
       binding.user.text = post.user?.name
        binding.username.text = "@${post.user?.name}"
        binding.content.text = post.content
        if (post.images !=null){
            try {
                Glide.with(binding.item)
                    .load("${Consts.TWITTER_API_BASE_URL}/${post.images[0].url}")
                    .centerCrop()
                    .into(binding.img)
            }catch (e:Exception){
                Toast.makeText(binding.item.context, e.message, Toast.LENGTH_SHORT).show()
            }
            val images = arrayListOf<MyImage>()
            for(image in post.images){
               images.add(MyImage(null,false, "${Consts.TWITTER_API_BASE_URL}/${image.url}"))
            }
            binding.images.adapter = ImagesAdapter(images){

            }
            binding.images.layoutManager = GridLayoutManager(binding.images.context,2)
        }
    }
}