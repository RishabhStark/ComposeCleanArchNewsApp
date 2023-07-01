package com.example.composenewsapp.feature_get_news.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.composenewsapp.feature_get_news.data.NewsRepository
import com.example.composenewsapp.feature_get_news.data.remote.dto.NewsDto
import com.example.composenewsapp.utils.NewsPaginator
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class NewsSearchScreenViewModel(private val repository: NewsRepository) : ViewModel() {
    private val _searchQuery = MutableStateFlow("")
    val searchQuery = _searchQuery.asStateFlow()
    var job: Job? = null
    private val _newsState = MutableStateFlow(NewsState(page = 1))
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
            val result = repository.searchArticles(q = _searchQuery.value, pageNumber = it)
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
                    endReached = newsDto.articles.isEmpty()
                )
        }
    )

    fun loadNextItems() {
        viewModelScope.launch {
            paginator.loadNextItems()
        }
    }


    fun onSearch(query: String) {
        _searchQuery.value = query
        //reset the page and data on new query
        paginator.reset()
        _newsState.value = NewsState(page = 1)
        job?.cancel()
        job = viewModelScope.launch {
            delay(500L)
            loadNextItems()
        }
    }
}