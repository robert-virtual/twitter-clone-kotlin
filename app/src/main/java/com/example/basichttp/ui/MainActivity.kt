package com.example.basichttp.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.recyclerview.widget.DividerItemDecoration
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
            initRecyclerView(it)
        }
        viewModel.error.observe(this){
            binding.loader.visibility = View.GONE
            binding.error.visibility = View.VISIBLE
            binding.btnTryAgain.visibility = View.VISIBLE
            binding.error.text = it
        }
        listeners()
    }
    fun listeners(){
        binding.btnTryAgain.setOnClickListener {
            getData()
        }
        binding.btnAdd.setOnClickListener {
            addPost()
        }
        binding.swipeRefresh.setOnRefreshListener {
            getData(false)
            binding.swipeRefresh.isRefreshing = false
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
    fun getData(loader:Boolean = true){
        viewModel.getPosts()
        binding.error.visibility = View.GONE
        binding.btnTryAgain.visibility = View.GONE
        binding.loader.visibility = if(loader){
            View.VISIBLE
        }else{
            View.GONE
        }
    }
    fun initRecyclerView( data:List<Post>){
        val manager = LinearLayoutManager(this)
        val decorator = DividerItemDecoration(this,manager.orientation)
        postsAdapter = PostsAdapter(data)
        binding.postsList.adapter = postsAdapter
        binding.postsList.layoutManager = manager
        binding.postsList.addItemDecoration(decorator)
    }
}