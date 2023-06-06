package com.example.composenewsapp.feature_get_news.presentation

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun NewsSearchScreen(modifier: Modifier = Modifier) {
    Column(modifier = modifier.fillMaxSize() ) {
        Text(text = "No results found")
    }
}