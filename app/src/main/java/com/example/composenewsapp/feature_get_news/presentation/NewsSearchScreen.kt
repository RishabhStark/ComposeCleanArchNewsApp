package com.example.composenewsapp.feature_get_news.presentation

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.ui.Modifier

@Composable
fun NewsSearchScreen(modifier: Modifier = Modifier, disableSearchCallback: () -> Unit = {}) {
    Column(modifier = modifier.fillMaxSize()) {
        Text(text = "No results found")
    }
    DisposableEffect(Unit) {
        onDispose {
            // Invoke the callback when the composable is removed from the composition tree
            disableSearchCallback()
        }
    }
}