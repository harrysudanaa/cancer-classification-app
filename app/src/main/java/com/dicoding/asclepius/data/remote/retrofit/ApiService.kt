package com.dicoding.asclepius.data.remote.retrofit

import com.dicoding.asclepius.data.remote.response.NewsArticleResponse
import org.intellij.lang.annotations.Language
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {
    @GET("v2/top-headlines")
    fun getNewsArticle(
        @Query("q") q : String,
        @Query("category") category : String,
        @Query("language") language : String,
        @Query("apiKey") apiKey : String
    ) : Call<NewsArticleResponse>
}