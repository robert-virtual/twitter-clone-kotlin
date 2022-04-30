package com.example.basichttp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.basichttp.adapters.PostsAdapter
import com.example.basichttp.databinding.ActivityMainBinding
import com.example.basichttp.model.Post
import com.example.basichttp.ui.MainViewModel
import com.example.basichttp.ui.MyBottomSheet

class MainActivity : AppCompatActivity() {
    private lateinit var binding:ActivityMainBinding
    private val viewModel: MainViewModel by viewModels()
    private val posts = mutableListOf<Post>()
    private val postsAdapter:PostsAdapter  = PostsAdapter(posts)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initRecyclerView()
        getData()
        viewModel.posts.observe(this){
            hideError()
            posts.addAll(it)
            Toast.makeText(this, "Posts ${posts.size}", Toast.LENGTH_SHORT).show()
            postsAdapter.notifyDataSetChanged()
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
        val modalBottomSheet = MyBottomSheet()
        modalBottomSheet.show(supportFragmentManager,MyBottomSheet.TAG)
        /*
        val fragmentManager = supportFragmentManager
        val newFragment = CustomDialog()
        val transaction = fragmentManager.beginTransaction()
        transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
        transaction.add(android.R.id.content,newFragment)
            .addToBackStack(null)
            .commit()
         */
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
    fun initRecyclerView(){
        binding.postsList.adapter = postsAdapter
        binding.postsList.layoutManager = LinearLayoutManager(this)
    }
}