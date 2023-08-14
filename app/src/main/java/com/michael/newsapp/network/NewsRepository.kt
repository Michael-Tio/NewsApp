package com.michael.newsapp.network

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.michael.newsapp.model.ArticlesItem
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import android.util.Log
import com.michael.newsapp.model.NewsResponse

class NewsRepository {

    private val _topHeadlineData = MutableLiveData<List<ArticlesItem>>()
    val topHeadlineData: LiveData<List<ArticlesItem>> = _topHeadlineData

    private val _everythingData = MutableLiveData<List<ArticlesItem>>()
    val everythingData: LiveData<List<ArticlesItem>> = _everythingData


    fun getTopHeadlines(country: String, page: String) {
        val client = ApiConfig.getApiService().getTopHeadlines(country, page, "10")
        client.enqueue(object : Callback<NewsResponse> {
            override fun onResponse(
                call: Call<NewsResponse>,
                response: Response<NewsResponse>
            ) {
                if (response.isSuccessful) {
                    val articles = response.body()?.articles
                    _topHeadlineData.value = articles as List <ArticlesItem>?

                } else {
                    Log.d(TAG, "Error")
                }
            }

            override fun onFailure(call: Call<NewsResponse>, t: Throwable) {
                Log.d(TAG, t.message.toString())
            }
        })
    }

    fun getEverything(query: String, page: String) {
        val client = ApiConfig.getApiService().getEverything(query, page, "10")
        client.enqueue(object : Callback<NewsResponse> {
            override fun onResponse(
                call: Call<NewsResponse>,
                response: Response<NewsResponse>
            ) {
                if (response.isSuccessful) {
                    val articles = response.body()?.articles
                    _everythingData.value = articles as List<ArticlesItem>?

                } else {
                    Log.d(TAG, "Error")
                }
            }

            override fun onFailure(call: Call<NewsResponse>, t: Throwable) {
                Log.d(TAG, t.message.toString())
            }
        })
    }

    companion object {
        private const val TAG = "NewsRepository"
    }
}