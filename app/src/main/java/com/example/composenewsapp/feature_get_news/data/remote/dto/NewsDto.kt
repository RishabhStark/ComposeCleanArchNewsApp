package com.example.composenewsapp.feature_get_news.data.remote.dto

data class NewsDto(
    val articles: List<Article>,
    val status: String,
    val totalResults: Int
)