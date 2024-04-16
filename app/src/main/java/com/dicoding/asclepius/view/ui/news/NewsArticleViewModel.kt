package com.dicoding.asclepius.view.ui.news

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.dicoding.asclepius.repository.NewsArticleRepository

class NewsArticleViewModel(application: Application) : AndroidViewModel(application) {
    private val newsArticleRepository = NewsArticleRepository()

    val newsArticle = newsArticleRepository.newsArticle
    val isLoading = newsArticleRepository.isLoading

    fun getNewsArticle() = newsArticleRepository.getNewsArticle()
}