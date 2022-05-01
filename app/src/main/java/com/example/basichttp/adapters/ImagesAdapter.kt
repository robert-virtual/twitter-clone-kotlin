package com.example.basichttp.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.basichttp.R
import com.example.basichttp.model.MyImage

class ImagesAdapter(private val images:List<MyImage>,private val onSelectedImage:(MyImage)->Unit):RecyclerView.Adapter<ImagesViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImagesViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return ImagesViewHolder(layoutInflater.inflate(R.layout.image_adaptable,parent,false))

    }

    override fun onBindViewHolder(holder: ImagesViewHolder, position: Int) {
        holder.render(images[position], onSelectedImage)
    }

    override fun getItemCount(): Int {
        return images.size
    }

}