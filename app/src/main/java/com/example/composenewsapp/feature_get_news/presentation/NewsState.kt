package com.example.composenewsapp.feature_get_news.presentation

import com.example.composenewsapp.feature_get_news.data.remote.dto.NewsDto

data class NewsState(
    val headlines: List<NewsItem> = emptyList(),
    val isLoading: Boolean = false,
    val endReached: Boolean = false,
    val error: String? = null,
    val page: Int = 0
)

data class NewsItem(
    val title: String? = "",
    val content: String? = "",
    val imageUrl: String? = null,
    val articleUrl: String = ""
)