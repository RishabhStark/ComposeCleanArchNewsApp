package com.example.composenewsapp.feature_get_news.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.State
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import com.example.composenewsapp.feature_get_news.utils.Screen

@Composable
fun NewsSearchScreen(
    modifier: Modifier = Modifier,
    navController: NavHostController,
    newsState: State<NewsState>,
    loadNextItems: () -> Unit,
    onSearch: (String) -> Unit,
    searchState: State<String>
) {
    val lazyListState = rememberLazyListState()
    Column(modifier = Modifier.fillMaxSize()) {
        TextField(value = searchState.value, onValueChange = onSearch, modifier = Modifier
            .clickable {
            }
            .fillMaxWidth())
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colors.primary), state = lazyListState
        ) {
            items(newsState.value.headlines.size) { index ->
                LaunchedEffect(key1 = lazyListState)
                {
                    if (index + 1 >= newsState.value.headlines.size) {
                        loadNextItems()
                    }
                }
                NewsCard(
                    navController = navController,
                    title = newsState.value.headlines[index].title ?: "Title Not Available",
                    content = newsState.value.headlines[index].content
                        ?: "Content Not Available",
                    imageUrl = newsState.value.headlines[index].imageUrl ?: "",
                    articleUrl = newsState.value.headlines[index].articleUrl
                )
            }
        }
    }
}