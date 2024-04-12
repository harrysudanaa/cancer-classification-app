package com.dicoding.asclepius.view

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
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
            val imageUri = Uri.parse(history.imageUri)
            // permission error when show the image
            val flag = Intent.FLAG_GRANT_READ_URI_PERMISSION
            context.contentResolver.takePersistableUriPermission(imageUri, flag)
            binding.ivResultImage.setImageURI(imageUri)
            binding.tvResultClassification.text = history.label
            binding.tvResultScore.text = history.score.toString()
        }
    }
}