package com.dicoding.asclepius.view

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.dicoding.asclepius.data.remote.response.ArticlesItem
import com.dicoding.asclepius.databinding.ListArticleItemBinding

class NewsArticleAdapter(private val context: Context) :
    ListAdapter<ArticlesItem, NewsArticleAdapter.MyViewHolder>(DIFF_CALLBACK) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val binding =
            ListArticleItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val user = getItem(position)
        holder.bind(user, context)
    }

    class MyViewHolder(private val binding: ListArticleItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(article: ArticlesItem, context: Context) {
            // when article removed
            if (article.title != "[Removed]") {
                binding.tvTitleArticle.text = article.title
                binding.tvDescriptionArticle.text = article.description
                Glide.with(binding.ivArticle.context)
                    .load(article.urlToImage)
                    .into(binding.ivArticle)
                binding.btnMoreInfo.setOnClickListener {
                    val intent = Intent(Intent.ACTION_VIEW, Uri.parse(article.url))
                    context.startActivity(intent)
                }
            } else {
                binding.tvTitleArticle.visibility = View.GONE
                binding.tvDescriptionArticle.visibility = View.GONE
                binding.ivArticle.visibility = View.GONE
                binding.btnMoreInfo.visibility = View.GONE
                binding.articleContainer.visibility = View.GONE

                // reset margin top
                val layoutParams = binding.cvArticle.layoutParams as? ViewGroup.MarginLayoutParams
                layoutParams?.topMargin = 0
                binding.cvArticle.layoutParams = layoutParams
            }
        }
    }

    companion object {
        val DIFF_CALLBACK = object : DiffUtil.ItemCallback<ArticlesItem>() {
            override fun areItemsTheSame(oldItem: ArticlesItem, newItem: ArticlesItem): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(oldItem: ArticlesItem, newItem: ArticlesItem): Boolean {
                return oldItem == newItem
            }

        }
    }

}