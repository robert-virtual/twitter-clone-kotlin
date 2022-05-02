package com.example.basichttp.ui

import android.content.ContentUris
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.view.View
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.basichttp.adapters.ImagesAdapter
import com.example.basichttp.databinding.ActivityNewTweetBinding
import com.example.basichttp.model.MyImage
import com.example.basichttp.viewmodel.NewTweetViewModel
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.snackbar.Snackbar

class NewTweet : AppCompatActivity() {
    val viewModel:NewTweetViewModel by viewModels()
    private lateinit var binding: ActivityNewTweetBinding
    private lateinit var selectedImagesAdapter:ImagesAdapter

    private lateinit var imagesAdapter:ImagesAdapter

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
        initRecyclerView()
        requestpermission()
        btnsListeners()
        viewModel.post.observe(this){
            binding.loader.visibility = View.GONE
            binding.tweet.text.clear()
            viewModel.selectedImages.clear()
            finish()
        }
        viewModel.error.observe(this){
            binding.selectedImages.visibility = View.VISIBLE
            binding.tweet.isEnabled = true
            binding.loader.visibility = View.GONE
            Snackbar.make(binding.btnPublish,it,Snackbar.LENGTH_SHORT)
                .show()
        }
    }
    fun btnsListeners(){
        binding.btnClose.setOnClickListener {
            finish()
        }
        binding.btnPublish.setOnClickListener {
            binding.selectedImages.visibility = View.GONE
            binding.tweet.isEnabled = false
            binding.loader.visibility = View.VISIBLE
            viewModel.createPost(binding.tweet.text.toString())
        }

    }
    override fun finish() {
        if (viewModel.selectedImages.size >0 || binding.tweet.text.toString() != ""){
            val dialog = MaterialAlertDialogBuilder(this)
                .setTitle("Descartar Tweet")
                .setMessage("Se perdara el contenido de este tweet")
                .setPositiveButton("Continuar con este tweet"){dialog,which->

                }
                .setNegativeButton("Descartar Tweet"){dialog,which->
                    super.finish()
                }
            dialog.show()
        }else{
            super.finish()
        }

    }
    private fun initRecyclerView(){
        selectedImagesAdapter = ImagesAdapter(viewModel.selectedImages){
            removeImage(it)
        }
        imagesAdapter = ImagesAdapter(viewModel.images){
            onSelectedImage(it)
        }
        //binding.recyclerView.layoutManager = GridLayoutManager(this, 4)
        binding.recyclerView.layoutManager = LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false)
        binding.recyclerView.adapter = imagesAdapter
        // imagenes seleccionadas
        binding.selectedImages.layoutManager = GridLayoutManager(this,2)
        binding.selectedImages.adapter = selectedImagesAdapter
    }
    private fun requestpermission(){
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
    private fun removeImage(image:MyImage){
        val itemIdx = viewModel.selectedImages.indexOf(image)
        viewModel.selectedImages.remove(image)
        selectedImagesAdapter.notifyItemRemoved(itemIdx)
    }

    private fun onSelectedImage(image:MyImage){
        if (viewModel.selectedImages.size == 5){
            Snackbar.make(binding.recyclerView,"Solo puedes agregar 5 imagenes",Snackbar.LENGTH_SHORT)
                .show()

            return
        }

        val query = contentResolver.query(image.uri!!,null,null,null,null)
        query.use { cursor->
            val nameColumn = cursor?.getColumnIndexOrThrow(MediaStore.Images.Media.DISPLAY_NAME)
            val mimeTypeColumn = cursor?.getColumnIndexOrThrow(MediaStore.Images.Media.MIME_TYPE)
            cursor?.moveToFirst()
            if (nameColumn !== null && mimeTypeColumn != null){
                val name = cursor.getString(nameColumn)
                val mimeType = cursor.getString(mimeTypeColumn)
                val byteArray = contentResolver.openInputStream(image.uri)?.readBytes()
                viewModel.selectedImages.add(MyImage(image.uri,true,null,true,byteArray,name,mimeType))
                selectedImagesAdapter.notifyItemInserted(viewModel.selectedImages.size-1)
            }
        }
    }
    private fun loadImages(){
        if (viewModel.images.size > 0) return
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
                    viewModel.images.add(MyImage(uri,false))
                    imagesAdapter.notifyItemInserted(viewModel.images.size-1)

                }while(cursor.moveToNext())


                Toast.makeText(this, "${cursor.count} imagenes encontradas", Toast.LENGTH_SHORT).show()
            }
        }
        cursor?.close()
    }

}