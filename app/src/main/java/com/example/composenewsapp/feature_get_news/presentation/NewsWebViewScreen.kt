package com.example.composenewsapp.feature_get_news.presentation

import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.viewinterop.AndroidView

@Composable
fun NewsWebViewScreen(
    modifier: Modifier = Modifier,
    url: String,
    onRemoved: () -> Unit
) {
    DisposableEffect(Unit) {
        onDispose {
            // Invoke the callback when the composable is removed from the composition tree
            onRemoved()
        }
    }
    AndroidView(modifier = modifier.fillMaxSize(), factory = { context ->
        WebView(context).apply {
            webViewClient = WebViewClient()
            loadUrl(url)
        }
    })
}