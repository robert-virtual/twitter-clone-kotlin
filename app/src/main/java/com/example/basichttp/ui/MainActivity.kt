package com.example.basichttp.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.basichttp.adapters.PostsAdapter
import com.example.basichttp.databinding.ActivityMainBinding
import com.example.basichttp.model.Post
import com.example.basichttp.viewmodel.MainViewModel

class MainActivity : AppCompatActivity() {
    private lateinit var binding:ActivityMainBinding
    private val viewModel: MainViewModel by viewModels()
    private lateinit var postsAdapter:PostsAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        getData()
        viewModel.posts.observe(this){
            hideError()
            Toast.makeText(this, "Posts ${viewModel.posts.value?.size}", Toast.LENGTH_SHORT).show()
            initRecyclerView(it)
        }
        viewModel.error.observe(this){
            binding.loader.visibility = View.GONE
            binding.error.visibility = View.VISIBLE
            binding.btnTryAgain.visibility = View.VISIBLE
            binding.error.text = it
        }
        binding.btnTryAgain.setOnClickListener {
            getData()
        }
        binding.btnAdd.setOnClickListener {
            addPost()
        }
    }
    fun addPost(){
        val i = Intent(this,NewTweet::class.java)
        startActivity(i)
    }
    fun hideError(){
        binding.error.visibility = View.GONE
        binding.btnTryAgain.visibility = View.GONE
        binding.loader.visibility = View.GONE
    }
    fun getData(){
        viewModel.getPosts()
        binding.error.visibility = View.GONE
        binding.btnTryAgain.visibility = View.GONE
        binding.loader.visibility = View.VISIBLE
    }
    fun initRecyclerView( data:List<Post>){
        postsAdapter = PostsAdapter(data)
        binding.postsList.adapter = postsAdapter
        binding.postsList.layoutManager = LinearLayoutManager(this)
    }
}