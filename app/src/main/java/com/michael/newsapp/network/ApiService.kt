package com.michael.newsapp.network

import com.michael.newsapp.model.NewsResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {

    @GET("top-headlines")
    fun getTopHeadlines(
        @Query("country") country: String,
        @Query("page") page: String,
        @Query("pageSize") pageSize: String,
    ): Call<NewsResponse>

    @GET("everything")
    fun getEverything(
        @Query("q") query: String,
        @Query("page") page: String,
        @Query("pageSize") pageSize: String,
    ): Call<NewsResponse>

}