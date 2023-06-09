package com.example.composenewsapp.feature_get_news.data

import android.util.Log
import com.example.composenewsapp.feature_get_news.data.remote.NewsApiService
import com.example.composenewsapp.feature_get_news.data.remote.dto.NewsDto
import com.example.composenewsapp.utils.Resource
import okio.IOException
import retrofit2.HttpException
import retrofit2.Response

class NewsRepository(private val apiService: NewsApiService) {
    private val TAG = "NewsRepository"
    suspend fun getTopHeadlines(country: String = "in", pageNumber: Int): Resource<NewsDto> {
        var response: Response<NewsDto>?
        try {
            response = apiService.getTopHeadlines(country, pageNumber)
        } catch (httpException: HttpException) {
            return Resource.Error(message = httpException.message)
        } catch (ioException: IOException) {
            return Resource.Error(message = ioException.message)
        } catch (e: java.lang.Exception) {
            return Resource.Error(message = e.message)
        }
        if (!response.isSuccessful) {
            return Resource.Error(message = response.errorBody().toString())
        }
        Log.d(TAG, "getTopHeadlines: ${response.body()}")
        return Resource.Success(response.body())
    }

    suspend fun searchArticles(q: String = "", pageNumber: Int = 1): Resource<NewsDto> {
        var response: Response<NewsDto>?
        try {
            response = apiService.searchArticles(q, pageNumber)
            Log.d(
                TAG,
                "searchArticles: making api call query: $q page number: $pageNumber--> ${response.body()}"
            )
        } catch (httpException: HttpException) {
            Log.d(TAG, "searchArticles: ${httpException.message}")
            return Resource.Error(message = httpException.message)
        } catch (ioException: IOException) {
            Log.d(TAG, "searchArticles: ${ioException.message}")
            return Resource.Error(message = ioException.message)
        } catch (e: java.lang.Exception) {
            Log.d(TAG, "searchArticles: ${e.message}")
            return Resource.Error(message = e.message)
        }
        if (!response.isSuccessful) {
            return Resource.Error(message = response.errorBody().toString())
        }
        Log.d(TAG, "searchArticles: ${response.body()?.articles?.size}")
        return Resource.Success(response.body())
    }
}