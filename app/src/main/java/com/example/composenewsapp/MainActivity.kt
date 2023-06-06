package com.example.composenewsapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.isSystemInDarkTheme
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
import androidx.compose.ui.graphics.toArgb
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.composenewsapp.feature_get_news.data.NewsRepository
import com.example.composenewsapp.feature_get_news.data.remote.RetrofitClient
import com.example.composenewsapp.feature_get_news.presentation.*
import com.example.composenewsapp.feature_get_news.utils.Screen
import com.example.composenewsapp.ui.theme.ComposeNewsAppTheme

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
                if (isSystemInDarkTheme()) {
                    window.statusBarColor = MaterialTheme.colors.background.toArgb()
                }
                Scaffold(modifier = Modifier.fillMaxSize(), topBar = {
                    var enabled by remember {
                        mutableStateOf(false)
                    }
                    TopAppBar(modifier = Modifier.fillMaxWidth()) {
                        TextField(
                            value = "",
                            onValueChange = {},
                            Modifier
                                .fillMaxWidth()
                                .background(MaterialTheme.colors.background)
                                .clickable {
                                    navHostController.navigate(Screen.NewsSearchScreen.route)
                                    enabled=true
                                }, enabled = enabled
                        )
                    }
                }) {
                    NavGraph(
                        modifier = Modifier.padding(it),
                        navHostController = navHostController,
                        viewModel = viewModel
                    )
                }

            }

        }
    }
}
