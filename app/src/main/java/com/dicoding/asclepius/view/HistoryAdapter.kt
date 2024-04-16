package com.dicoding.asclepius.view

import android.annotation.SuppressLint
import android.graphics.BitmapFactory
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.dicoding.asclepius.data.local.entity.HistoryClassification
import com.dicoding.asclepius.databinding.ListHistoryItemBinding
import java.text.NumberFormat

class HistoryAdapter :
    ListAdapter<HistoryClassification, HistoryAdapter.MyViewHolder>(DIFF_CALLBACK) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val binding =
            ListHistoryItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val history = getItem(position)
        holder.bind(history)
    }

    class MyViewHolder(private val binding: ListHistoryItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        @SuppressLint("SetTextI18n")
        fun bind(history: HistoryClassification) {
            val imageBitmap = history.imageData?.let {
                BitmapFactory.decodeByteArray(
                    history.imageData, 0, it.size
                )
            }
            binding.ivResultImage.setImageBitmap(imageBitmap)
            binding.tvResultClassification.text = history.label
            binding.tvResultScore.text =
                NumberFormat.getPercentInstance().format(history.score).toString()
            binding.tvDate.text = history.date
        }
    }

    companion object {
        val DIFF_CALLBACK = object : DiffUtil.ItemCallback<HistoryClassification>() {
            override fun areItemsTheSame(
                oldItem: HistoryClassification,
                newItem: HistoryClassification
            ): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(
                oldItem: HistoryClassification,
                newItem: HistoryClassification
            ): Boolean {
                return oldItem == newItem
            }

        }
    }

}