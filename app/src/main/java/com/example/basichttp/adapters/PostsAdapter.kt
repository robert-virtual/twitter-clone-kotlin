package com.example.basichttp.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.basichttp.R
import com.example.basichttp.model.Post

class PostsAdapter (private val posts:List<Post>):RecyclerView.Adapter<PostsViewHolder>(){
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostsViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return PostsViewHolder(layoutInflater.inflate(R.layout.post_item,parent,false))
    }

    override fun onBindViewHolder(holder: PostsViewHolder, position: Int) {
        holder.render(posts[position])
    }

    override fun getItemCount(): Int {
        return posts.size
    }
}