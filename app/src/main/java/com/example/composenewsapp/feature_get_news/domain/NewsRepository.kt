package com.example.composenewsapp.feature_get_news.domain

import com.example.composenewsapp.feature_get_news.data.remote.dto.NewsDto
import com.example.composenewsapp.utils.Resource

interface NewsRepository {
    suspend fun getTopHeadlines(country: String = "in", pageNumber: Int): Resource<NewsDto>
    suspend fun searchArticles(q: String = "", pageNumber: Int = 1): Resource<NewsDto>

}