package com.dicoding.asclepius.view.ui.history

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.dicoding.asclepius.repository.HistoryRepository

class HistoryViewModel(application: Application) : AndroidViewModel(application) {
    private val historyRepository = HistoryRepository(application)

    fun getAllHistory() = historyRepository.getAllHistory()
}