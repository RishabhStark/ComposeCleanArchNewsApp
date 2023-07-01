package com.example.composenewsapp.feature_get_news.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.composenewsapp.feature_get_news.data.NewsRepository

class NewsViewModelFactory(private val newsRepository: NewsRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(NewsViewModel::class.java)) {
            return NewsViewModel(newsRepository) as T
        }
        throw java.lang.IllegalArgumentException("Unknown ViewModel class")
    }
}
class SearchNewsViewModelFactory(private val newsRepository: NewsRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(NewsSearchScreenViewModel::class.java)) {
            return NewsSearchScreenViewModel(newsRepository) as T
        }
        throw java.lang.IllegalArgumentException("Unknown ViewModel class")
    }
}