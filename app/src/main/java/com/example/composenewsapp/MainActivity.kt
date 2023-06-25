package com.example.composenewsapp

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.composenewsapp.feature_get_news.data.NewsRepository
import com.example.composenewsapp.feature_get_news.data.remote.RetrofitClient
import com.example.composenewsapp.feature_get_news.presentation.NavGraph
import com.example.composenewsapp.feature_get_news.presentation.NewsViewModel
import com.example.composenewsapp.feature_get_news.presentation.NewsViewModelFactory
import com.example.composenewsapp.feature_get_news.utils.Screen
import com.example.composenewsapp.ui.theme.ComposeNewsAppTheme
import kotlinx.coroutines.*

class MainActivity : ComponentActivity() {
    private lateinit var navHostController: NavHostController
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            ComposeNewsAppTheme {
                navHostController = rememberNavController()
                val viewModel = ViewModelProvider(
                    this,
                    NewsViewModelFactory(newsRepository = NewsRepository(RetrofitClient.newsApi))
                )[NewsViewModel::class.java]
                actionBar?.hide()
                val scaffoldState = rememberScaffoldState()
                val shouldShowTopAppbar = rememberSaveable() {
                    mutableStateOf(true)
                }
                var enabled by rememberSaveable {
                    mutableStateOf(false)
                }
                Scaffold(
                    scaffoldState = scaffoldState,
                    modifier = Modifier.fillMaxSize(),
                    topBar = {
                        if (shouldShowTopAppbar.value) {
                            TopAppBar(modifier = Modifier.fillMaxWidth()) {
                                TextField(
                                    value = "",
                                    onValueChange = {},
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .background(MaterialTheme.colors.background)
                                        .clickable {
                                            navHostController.navigate(Screen.NewsSearchScreen.route)
                                            enabled = true
                                        },
                                    enabled = enabled
                                )
                            }
                        }
                    }) {
                    NavGraph(
                        modifier = Modifier.padding(it),
                        navHostController = navHostController,
                        viewModel = viewModel,
                        callback = { shouldShowTopAppbar.value = false },
                        onRemoved = { shouldShowTopAppbar.value = true },
                        disableSearchCallback = { enabled = false }
                    )
                }

            }

        }
    }
}
