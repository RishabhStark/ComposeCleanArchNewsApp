package com.example.composenewsapp.feature_get_news.presentation

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.State
import androidx.compose.ui.Modifier
import androidx.navigation.NavController


@Composable
fun NewsHomeScreen(
    navController: NavController,
    newsState: State<NewsState>,
    loadNextItems: () -> Unit
) {
    val lazyListState = rememberLazyListState()
    LazyColumn(modifier = Modifier.fillMaxSize(), state = lazyListState) {
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
                content = newsState.value.headlines[index].content ?: "Content Not Available",
                imageUrl = newsState.value.headlines[index].imageUrl ?: "",
                articleUrl = newsState.value.headlines[index].articleUrl
            )
        }
    }
}