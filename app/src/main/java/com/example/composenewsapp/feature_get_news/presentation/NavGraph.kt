package com.example.composenewsapp.feature_get_news.presentation

import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.composenewsapp.feature_get_news.data.NewsRepositoryImpl
import com.example.composenewsapp.feature_get_news.data.remote.RetrofitClient
import com.example.composenewsapp.feature_get_news.utils.Screen

private const val TAG = "NavGraph"

@Composable
fun NavGraph(
    modifier: Modifier = Modifier,
    navHostController: NavHostController = rememberNavController(),
    startDestination: String = Screen.NewsHomeScreen.route
) {
    NavHost(
        modifier = modifier,
        navController = navHostController,
        startDestination = startDestination
    ) {
        composable(route = Screen.NewsHomeScreen.route) {
            val viewModel: NewsViewModel =
                viewModel(factory = NewsViewModelFactory(NewsRepositoryImpl(RetrofitClient.newsApi)))
            NewsHomeScreen(
                navHostController,
                viewModel.newsState.collectAsState(),
                viewModel::loadNextItems
            )
        }
        composable(
            route = Screen.NewsWebViewScreen.route,
            arguments = listOf(navArgument("article_url") { type = NavType.StringType })
        ) { navBackStackEntry ->
            val articleUrl = navBackStackEntry.arguments?.getString("article_url")
            articleUrl?.let { articleUrl ->
                NewsWebViewScreen(url = articleUrl)
                Log.d(TAG, "article url arg: $articleUrl")
            }
        }
        composable(route = Screen.NewsSearchScreen.route) {
            val viewModel: NewsSearchScreenViewModel =
                viewModel(factory = SearchNewsViewModelFactory(NewsRepositoryImpl(RetrofitClient.newsApi)))
            NewsSearchScreen(
                navController = navHostController,
                newsState = viewModel.newsState.collectAsState(),
                loadNextItems = viewModel::loadNextItems,
                onSearch = viewModel::onSearch,
                searchState = viewModel.searchQuery.collectAsState()
            )
        }
    }
}

@Composable
fun <T> NavBackStackEntry.sharedViewModel(navHostController: NavHostController) {
val parentBackStackEntry=destination.parent?.route
}