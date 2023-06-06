package com.example.composenewsapp.utils

interface Paginator<Item,Key> {
    suspend fun loadNextItems()
    fun reset()
}