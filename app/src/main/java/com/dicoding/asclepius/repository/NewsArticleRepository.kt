package com.dicoding.asclepius.repository

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.dicoding.asclepius.BuildConfig
import com.dicoding.asclepius.data.remote.response.ArticlesItem
import com.dicoding.asclepius.data.remote.response.NewsArticleResponse
import com.dicoding.asclepius.data.remote.retrofit.ApiConfig
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class NewsArticleRepository {

    private val _newsArticle = MutableLiveData<List<ArticlesItem>?>()
    val newsArticle: LiveData<List<ArticlesItem>?> = _newsArticle

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    fun getNewsArticle() {
        _isLoading.value = true
        val client = ApiConfig.getApiService().getNewsArticle(
            q = "cancer",
            category = "health",
            language = "en",
            apiKey = BuildConfig.API_KEY
        )
        client.enqueue(object : Callback<NewsArticleResponse> {
            override fun onResponse(
                call: Call<NewsArticleResponse>,
                response: Response<NewsArticleResponse>
            ) {
                _isLoading.value = false
                if (response.isSuccessful) {
                    _newsArticle.value = response.body()?.articles
                    Log.d("REPO", "${response.body()}")
                } else {
                    Log.e(TAG, "onFailure: ${response.message()}")
                }
            }

            override fun onFailure(call: Call<NewsArticleResponse>, t: Throwable) {
                _isLoading.value = false
                Log.e(TAG, "onFailure: ${t.message}")
            }

        })
    }

    companion object {
        private const val TAG = "HistoryRepository"
    }
}