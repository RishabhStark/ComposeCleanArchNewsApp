package com.example.composenewsapp.feature_get_news.presentation

import android.graphics.drawable.shapes.Shape
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.composenewsapp.feature_get_news.utils.Screen
import kotlinx.coroutines.launch

@Composable
fun NewsSearchScreen(
    modifier: Modifier = Modifier,
    navController: NavHostController,
    newsState: State<NewsState>,
    loadNextItems: () -> Unit,
    onSearch: (String) -> Unit,
    searchState: State<String>
) {
    val scope = rememberCoroutineScope()
    val lazyListState = rememberLazyListState()
    val isFabVisible = remember { derivedStateOf { lazyListState.firstVisibleItemIndex > 0 } }

    Box(modifier = Modifier.fillMaxSize()) {
        Column(modifier = Modifier.fillMaxSize()) {
            AnimatedVisibility(
                visible = !isFabVisible.value,
                enter = fadeIn(),
                exit = fadeOut()
            ) {
                TextField(
                    placeholder = {
                        Text(text = "Enter text to search...")
                    },
                    value = searchState.value, onValueChange = onSearch, modifier = Modifier
                        .fillMaxWidth(),
                    shape = RoundedCornerShape(8.dp),
                    singleLine = true
                )
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
        if (isFabVisible.value) {
            FloatingActionButton(onClick = {
                scope.launch { lazyListState.animateScrollToItem(0) }
            }, modifier = Modifier.align(Alignment.BottomCenter)) {
                Icon(Icons.Default.KeyboardArrowUp, "")
            }
        }
    }
}