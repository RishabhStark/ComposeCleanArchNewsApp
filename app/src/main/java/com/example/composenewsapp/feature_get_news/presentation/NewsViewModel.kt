package com.example.composenewsapp.feature_get_news.presentation

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.composenewsapp.feature_get_news.data.NewsRepositoryImpl
import com.example.composenewsapp.feature_get_news.data.remote.dto.NewsDto
import com.example.composenewsapp.utils.NewsPaginator
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class NewsViewModel(private val newsRepositoryImpl: NewsRepositoryImpl) : ViewModel() {
    private val TAG = "NewsViewModel"
    private val _newsState = MutableStateFlow(NewsState(isLoading = true))
    val newsState = _newsState.asStateFlow()

    private val paginator = NewsPaginator<NewsDto, Int>(
        initialKey = _newsState.value.page,
        onLoadUpdated = {
            _newsState.value = _newsState.value.copy(isLoading = it)
        },
        getNextKey = {
            _newsState.value.page + 1
        },
        onRequest = {
            val result = newsRepositoryImpl.getTopHeadlines(pageNumber = it)
            result
        },
        onError = {

        },
        onSuccess = { newsDto, nextKey ->
            _newsState.value =
                _newsState.value.copy(
                    headlines = _newsState.value.headlines + newsDto.articles.map {
                        NewsItem(
                            title = it.title,
                            content = it.content,
                            imageUrl = it.urlToImage,
                            articleUrl = it.url
                        )
                    }, page = nextKey,
                    endReached = newsDto.articles.isEmpty(),
                )
            Log.d(TAG, "onSuccess: pagination end reached: ${newsDto.articles.isEmpty()}")
        }
    )

    init {
        loadNextItems()
    }

    fun loadNextItems() {
        viewModelScope.launch {
            paginator.loadNextItems()
        }
    }
}