package com.dicoding.asclepius.view.ui.news

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.dicoding.asclepius.R
import com.dicoding.asclepius.data.remote.response.ArticlesItem
import com.dicoding.asclepius.databinding.FragmentNewsBinding
import com.dicoding.asclepius.view.NewsArticleAdapter

class NewsFragment : Fragment() {

    private var _binding: FragmentNewsBinding? = null
    private val binding get() = _binding!!
    private val newsArticleViewModel by lazy {
        ViewModelProvider(
            this,
            ViewModelProvider.AndroidViewModelFactory(requireActivity().application)
        )[NewsArticleViewModel::class.java]
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentNewsBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.rvNewsArticle.layoutManager = LinearLayoutManager(requireActivity())

        newsArticleViewModel.newsArticle.observe(viewLifecycleOwner) { article ->
            if (article.isNullOrEmpty()) {
                binding.tvMessageArticle.visibility = View.VISIBLE
                binding.tvMessageArticle.text = getString(R.string.no_article_data_message)
            } else {
                binding.tvMessageArticle.visibility = View.GONE
                setNewsArticleData(article)
            }
        }

        newsArticleViewModel.isLoading.observe(viewLifecycleOwner) {
            showLoading(it)
        }

        getNewsArticleData()
    }

    private fun getNewsArticleData() {
        newsArticleViewModel.getNewsArticle()
    }

    private fun showLoading(isLoading: Boolean?) {
        return if (isLoading == true) {
            binding.progressBarNews.visibility = View.VISIBLE
        } else {
            binding.progressBarNews.visibility = View.GONE
        }
    }

    private fun setNewsArticleData(article: List<ArticlesItem>) {
        val newsArticleAdapter = NewsArticleAdapter(requireContext())
        newsArticleAdapter.submitList(article)
        binding.rvNewsArticle.adapter = newsArticleAdapter
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}