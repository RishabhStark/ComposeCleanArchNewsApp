package com.example.composenewsapp.feature_get_news.data.remote

import com.example.composenewsapp.feature_get_news.data.remote.dto.NewsDto
import com.example.composenewsapp.utils.API_KEY
import com.example.composenewsapp.utils.Resource
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface NewsApiService {
    @GET("v2/top-headlines")
    suspend fun getTopHeadlines(
        @Query("country") country: String = "in",
        @Query("page") page: Int = 0,
        @Query("apiKey") apiKey: String = API_KEY
    ): Response<NewsDto>

    @GET("v2/everything")
    suspend fun searchArticles(
        @Query("q") query: String = "",
        @Query("page") page: Int = 1,
        @Query("pageSize") pageSize: Int = 20,
        @Query("apiKey") apiKey: String = API_KEY,
    ): Response<NewsDto>
}