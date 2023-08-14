package com.michael.newsapp.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.michael.newsapp.model.ArticlesItem
import com.michael.newsapp.network.NewsRepository

class HomeViewModel(private val repository: NewsRepository): ViewModel() {

    val topHeadlineData: LiveData<List<ArticlesItem>> = repository.topHeadlineData
    val everythingData: LiveData<List<ArticlesItem>> = repository.everythingData

    fun getTopHeadline(country: String, page: String) = repository.getTopHeadlines(country, page)
    fun getEverything(query: String, page: String) = repository.getEverything(query, page)
}