package com.example.composenewsapp.feature_get_news.presentation

import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.composenewsapp.feature_get_news.utils.Screen

private const val TAG = "NavGraph"

@Composable
fun NavGraph(
    modifier: Modifier = Modifier,
    navHostController: NavHostController = rememberNavController(),
    startDestination: String = Screen.NewsHomeScreen.route,
    viewModel: NewsViewModel,
    callback: () -> Unit = {},
    onRemoved: () -> Unit = {},
    disableSearchCallback: () -> Unit = {}
) {
    NavHost(
        modifier = modifier,
        navController = navHostController,
        startDestination = startDestination
    ) {
        composable(route = Screen.NewsHomeScreen.route) {
            NewsHomeScreen(
                navHostController,
                newsState = viewModel.newsState.collectAsState(),
                viewModel::loadNextItems
            )
        }
        composable(
            route = "news_web_view_screen/{article_url}",
            arguments = listOf(navArgument("article_url") { type = NavType.StringType })
        ) { navBackStackEntry ->
            val articleUrl = navBackStackEntry.arguments?.getString("article_url")
            articleUrl?.let { articleUrl ->
                NewsWebViewScreen(url = articleUrl, onRemoved = onRemoved)
                callback.invoke()
                Log.d(TAG, "article url arg: $articleUrl")
            }
        }
        composable(route = Screen.NewsSearchScreen.route) {
            NewsSearchScreen(disableSearchCallback=disableSearchCallback)
        }
    }
}