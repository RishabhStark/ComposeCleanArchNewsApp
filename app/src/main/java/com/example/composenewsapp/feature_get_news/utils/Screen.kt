package com.example.composenewsapp.feature_get_news.utils

sealed class Screen(val route: String) {
    object NewsHomeScreen : Screen("home")
    object BookMarksScreen : Screen("bookmarks")
    object NewsWebViewScreen : Screen("news_web_view_screen")
    object NewsSearchScreen : Screen("news_search_screen")
}
