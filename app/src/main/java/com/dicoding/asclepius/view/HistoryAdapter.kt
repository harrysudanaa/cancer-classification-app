package com.dicoding.asclepius.view

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.BitmapFactory
import android.net.Uri
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.dicoding.asclepius.data.local.entity.HistoryClassification
import com.dicoding.asclepius.databinding.ListHistoryItemBinding

class HistoryAdapter(private val context: Context) : RecyclerView.Adapter<HistoryAdapter.MyViewHolder>() {
    // TODO: Make Adapter to recycler view
    private var historyData = listOf<HistoryClassification>()

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.bind(historyData[position], context)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val binding = ListHistoryItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyViewHolder(binding)
    }

    override fun getItemCount(): Int = historyData.size

    fun setData(newData: List<HistoryClassification>) {
        historyData = newData
        notifyDataSetChanged()
    }

    class MyViewHolder(private val binding: ListHistoryItemBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(history: HistoryClassification, context: Context) {
            val imageBitmap = BitmapFactory.decodeByteArray(
                history.imageData, 0, history.imageData.size
            )
            binding.ivResultImage.setImageBitmap(imageBitmap)
            binding.tvResultClassification.text = history.label
            binding.tvResultScore.text = history.score.toString()
            binding.tvDate.text = history.date
        }
    }
}