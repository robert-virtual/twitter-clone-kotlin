package com.example.basichttp.adapters
import android.graphics.Bitmap
import android.util.Size
import android.view.View
import androidx.core.view.updateLayoutParams
import androidx.recyclerview.widget.RecyclerView
import com.example.basichttp.databinding.ImageAdaptableBinding
import com.example.basichttp.model.MyImage

class ImagesViewHolder(view:View):RecyclerView.ViewHolder(view) {
    val binding = ImageAdaptableBinding.bind(view)
    fun render(image:MyImage,onSelectedImage:(MyImage)->Unit){
        val thumbnail: Bitmap =
            binding.imgItem.context.contentResolver.loadThumbnail(
                image.uri, Size(640, 480), null)
        binding.img.setImageBitmap(thumbnail)
        if (image.delete){
            binding.btnDelete.setOnClickListener{
                onSelectedImage(image)
            }
        }else{
            binding.imgItem.setOnClickListener{
                onSelectedImage(image)
            }
            binding.img.updateLayoutParams {
                width = 150
                height = 150

            }
        }
        binding.btnDelete.visibility = if(image.delete){
            View.VISIBLE
        }else{
            View.GONE
        }
    }
}