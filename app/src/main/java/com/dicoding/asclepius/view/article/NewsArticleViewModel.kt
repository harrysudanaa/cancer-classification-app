package com.dicoding.asclepius.view.article

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.dicoding.asclepius.repository.NewsArticleRepository

class NewsArticleViewModel(application: Application) : AndroidViewModel(application) {
    private val newsArticleRepository = NewsArticleRepository()

    val newsArticle = newsArticleRepository.newsArticle

    fun getNewsArticle() = newsArticleRepository.getNewsArticle()
}