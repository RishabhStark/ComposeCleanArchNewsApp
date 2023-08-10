package com.example.composenewsapp.feature_get_news.presentation

import android.widget.ProgressBar
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.composenewsapp.feature_get_news.utils.Screen
import kotlinx.coroutines.launch


@Composable
fun NewsHomeScreen(
    navController: NavController,
    newsState: State<NewsState>,
    loadNextItems: () -> Unit
) {
    val scope = rememberCoroutineScope()
    val lazyListState = rememberLazyListState()
    val isFabVisible by remember { derivedStateOf { lazyListState.firstVisibleItemIndex > 0 } }

    Box(modifier = Modifier.fillMaxSize()) {
        Column(modifier = Modifier.fillMaxSize()) {
            AnimatedVisibility(
                visible = !isFabVisible,
                enter = fadeIn(),
                exit = fadeOut()
            ) {
                TextField(value = "",
                    placeholder = {
                        Text(text = "Enter text to search...")
                    },
                    onValueChange = {},
                    shape = RoundedCornerShape(8.dp),
                    modifier = Modifier
                        .clickable {
                            navController.navigate(Screen.NewsSearchScreen.route)
                        }
                        .fillMaxWidth(),
                    enabled = false)

            }
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
        if (isFabVisible) {
            FloatingActionButton(onClick = {
                scope.launch { lazyListState.animateScrollToItem(0) }
            }, modifier = Modifier.align(Alignment.BottomCenter)) {
                Icon(Icons.Default.KeyboardArrowUp, "")
            }
        }
        if (newsState.value.isLoading) {
            CircularProgressIndicator(
                color = Color.Blue,
                modifier = Modifier.align(Alignment.Center)
            )
        }
    }
}