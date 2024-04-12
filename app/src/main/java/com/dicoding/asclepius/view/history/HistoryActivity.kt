package com.dicoding.asclepius.view.history

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.dicoding.asclepius.data.local.entity.HistoryClassification
import com.dicoding.asclepius.databinding.ActivityHistoryBinding
import com.dicoding.asclepius.view.HistoryAdapter

class HistoryActivity : AppCompatActivity() {
    private lateinit var binding: ActivityHistoryBinding
    private val historyViewModel by lazy {
        ViewModelProvider(
            this,
            ViewModelProvider.AndroidViewModelFactory(application)
        )[HistoryViewModel::class.java]
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHistoryBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbarHistory.root)

        binding.rvHistory.layoutManager = LinearLayoutManager(this)

        historyViewModel.getAllHistory().observe(this) { history ->
            setHistoryData(history)
        }

    }

    private fun setHistoryData(history: List<HistoryClassification>) {
        val historyAdapter = HistoryAdapter(applicationContext)
        historyAdapter.setData(history)
        binding.rvHistory.adapter = historyAdapter
    }
}