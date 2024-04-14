package com.dicoding.asclepius.repository

import android.util.Log
import android.widget.Toast
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
    val newsArticle : MutableLiveData<List<ArticlesItem>?> = _newsArticle

    fun getNewsArticle() {
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
                if (response.isSuccessful) {
                    _newsArticle.value = response.body()?.articles
                    Log.d("REPO", "${response.body()}")
                } else {
                    Log.e(TAG, "onFailure: ${response.message()}")
                }
            }

            override fun onFailure(call: Call<NewsArticleResponse>, t: Throwable) {
                Log.e(TAG, "onFailure: ${t.message}")
            }

        })
    }

    companion object {
        private const val TAG = "HistoryRepository"
    }
}