package com.example.basichttp.ui

import android.content.ContentUris
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.basichttp.adapters.ImagesAdapter
import com.example.basichttp.databinding.ActivityNewTweetBinding
import com.example.basichttp.model.MyImage
import com.google.android.material.snackbar.Snackbar

class NewTweet : AppCompatActivity() {
    private lateinit var binding: ActivityNewTweetBinding
    val images = mutableListOf<MyImage>()
    val selectedImages = mutableListOf<MyImage>()
    private val selectedImagesAdapter = ImagesAdapter(selectedImages){
        removeImage(it)
    }

    private val imagesAdapter = ImagesAdapter(images){
        onSelectedImage(it)
    }
    val requestlauncher = registerForActivityResult(ActivityResultContracts.RequestPermission()){
        if (it){
            loadImages()
        }else{
            Toast.makeText(this, "Acceso a imagenes denegado", Toast.LENGTH_SHORT).show()
        }
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityNewTweetBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.hide()
        requestpermission()
        initRecyclerView()
    }
    fun initRecyclerView(){
        //binding.recyclerView.layoutManager = GridLayoutManager(this, 4)
        binding.recyclerView.layoutManager = LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false)
        binding.recyclerView.adapter = imagesAdapter
        // imagenes seleccionadas
        binding.selectedImages.layoutManager = GridLayoutManager(this,2)
        binding.selectedImages.adapter = selectedImagesAdapter
    }
    fun requestpermission(){
        when{
            ContextCompat.checkSelfPermission(
                applicationContext,
                android.Manifest.permission.READ_EXTERNAL_STORAGE,
            ) == PackageManager.PERMISSION_GRANTED->{
                loadImages()
            }
            else ->{
                requestlauncher.launch(
                    android.Manifest.permission.READ_EXTERNAL_STORAGE,
                )
            }
        }
    }
    fun removeImage(image:MyImage){
        val itemIdx = selectedImages.indexOf(image)
        selectedImages.remove(image)
        selectedImagesAdapter.notifyItemRemoved(itemIdx)
    }

    fun onSelectedImage(image:MyImage){
        if (selectedImages.size == 5){
            Snackbar.make(binding.recyclerView,"Solo puedes agregar 5 imagenes",Snackbar.LENGTH_SHORT)
                .show()

            return
        }
        selectedImages.add(MyImage(image.uri,true))
        selectedImagesAdapter.notifyItemInserted(selectedImages.size-1)
    }
    fun loadImages(){

        val resolver = contentResolver
        val uri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI
        val cursor  = resolver.query(uri,null,null,null,"${MediaStore.Images.Media.DATE_TAKEN} DESC")
        when{
            cursor == null ->{
                Toast.makeText(this, "Hubo un Error", Toast.LENGTH_SHORT).show()
            }
            !cursor.moveToFirst() ->{
                Toast.makeText(this, "No se encontraron imagenes", Toast.LENGTH_SHORT).show()
            }
            else ->{
                val idColumn = cursor.getColumnIndex(MediaStore.Images.Media._ID)
                do {
                    val id = cursor.getLong(idColumn)
                    val uri = ContentUris.withAppendedId(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,id)
                    images.add(MyImage(uri,false))
                    imagesAdapter.notifyItemInserted(images.size-1)

                }while(cursor.moveToNext())


                Toast.makeText(this, "${cursor.count} imagenes encontradas", Toast.LENGTH_SHORT).show()
            }
        }
        cursor?.close()
    }

}