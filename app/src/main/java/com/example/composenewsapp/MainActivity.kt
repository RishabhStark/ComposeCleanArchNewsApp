package com.example.composenewsapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Scaffold
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
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
import com.example.composenewsapp.feature_get_news.presentation.SearchNewsViewModelFactory
import com.example.composenewsapp.ui.theme.ComposeNewsAppTheme

class MainActivity : ComponentActivity() {
    private lateinit var navHostController: NavHostController
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ComposeNewsAppTheme {
                navHostController = rememberNavController()
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
                ) {
                    NavGraph(
                        modifier = Modifier.padding(it),
                        navHostController = navHostController
                    )
                }

            }

        }
    }
}
