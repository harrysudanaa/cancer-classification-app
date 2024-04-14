package com.dicoding.asclepius.view.article

import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.dicoding.asclepius.R
import com.dicoding.asclepius.data.remote.response.ArticlesItem
import com.dicoding.asclepius.databinding.ActivityNewsArticleBinding
import com.dicoding.asclepius.view.NewsArticleAdapter

class NewsArticleActivity : AppCompatActivity() {
    private lateinit var binding: ActivityNewsArticleBinding
    private val newsArticleViewModel by lazy {
        ViewModelProvider(
            this,
            ViewModelProvider.AndroidViewModelFactory(application)
        )[NewsArticleViewModel::class.java]
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityNewsArticleBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbarNewsArticle.root)

        binding.rvArticles.layoutManager = LinearLayoutManager(this)

        newsArticleViewModel.newsArticle.observe(this) {article ->
            article?.let { setNewsArticleData(article) }
        }

        getNewsArticleData()
    }

    private fun getNewsArticleData() {
        newsArticleViewModel.getNewsArticle()
    }

    private fun setNewsArticleData(article: List<ArticlesItem>) {
        val newsArticleAdapter = NewsArticleAdapter(this)
        newsArticleAdapter.submitList(article)
        binding.rvArticles.adapter = newsArticleAdapter
    }
}