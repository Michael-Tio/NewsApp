package com.michael.newsapp.ui.home

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.michael.newsapp.R
import com.michael.newsapp.databinding.ActivityHomeBinding
import com.michael.newsapp.model.ArticlesItem
import com.michael.newsapp.network.NewsRepository
import com.michael.newsapp.ui.ViewModelFactory

class HomeActivity : AppCompatActivity() {
    private lateinit var binding: ActivityHomeBinding
    private lateinit var viewModel: HomeViewModel

    private lateinit var everythingAdapter: EverythingAdapter
    private val allEverythingData:MutableList<ArticlesItem> = mutableListOf()
    private var isLoadingEverything = false
    private var everythingPage = 1

    private lateinit var headlineAdapter: HeadlineAdapter
    private val allHeadlineData:MutableList<ArticlesItem> = mutableListOf()
    private var isLoadingHeadline = false
    private var headlinePage = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.hide()

        val factory = ViewModelFactory(NewsRepository())
        viewModel = ViewModelProvider(this, factory)[HomeViewModel::class.java]

        binding.rvEverything.layoutManager = LinearLayoutManager(this)
        binding.rvHeadline.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)

        everythingAdapter = EverythingAdapter(allEverythingData)
        binding.rvEverything.adapter = everythingAdapter

        viewModel.getEverything("Trump", everythingPage.toString())
        viewModel.everythingData.observe(this){
            allEverythingData.addAll(it.toMutableList())
            everythingAdapter.notifyDataSetChanged()
            if (it.isNotEmpty()) {
                isLoadingEverything = false
            }
        }

        headlineAdapter = HeadlineAdapter(allHeadlineData)
        binding.rvHeadline.adapter = headlineAdapter

        viewModel.getTopHeadline("us", headlinePage.toString())
        viewModel.topHeadlineData.observe(this){
            allHeadlineData.addAll(it.toMutableList())
            headlineAdapter.notifyDataSetChanged()
            if (it.isNotEmpty()){
                isLoadingHeadline = false
            }
        }

        binding.rvEverything.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)

                val layoutManager = recyclerView.layoutManager as LinearLayoutManager
                val lastVisibleItemPosition = layoutManager.findLastCompletelyVisibleItemPosition()
                val totalItemCount = layoutManager.itemCount

                if (!isLoadingEverything && lastVisibleItemPosition == totalItemCount - 1) {
                    // Load more data
                    everythingPage += 1
                    loadMoreData("Everything")
                }
            }
        })

        binding.rvHeadline.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)

                val layoutManager = recyclerView.layoutManager as LinearLayoutManager
                val lastVisibleItemPosition = layoutManager.findLastCompletelyVisibleItemPosition()
                val totalItemCount = layoutManager.itemCount

                if (!isLoadingHeadline && lastVisibleItemPosition == totalItemCount - 1) {
                    // Load more data
                    headlinePage += 1
                    loadMoreData("Headline")
                }
            }
        })
    }

    private fun loadMoreData(rv: String) {
        if(rv == "Everything"){
            isLoadingEverything = true
            viewModel.getEverything("Trump", everythingPage.toString())
        }
        else if(rv == "Headline"){
            isLoadingHeadline = true
            viewModel.getTopHeadline("us", headlinePage.toString())
        }
    }
}