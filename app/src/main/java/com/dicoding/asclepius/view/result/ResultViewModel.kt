package com.dicoding.asclepius.view.result

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.dicoding.asclepius.data.local.entity.HistoryClassification
import com.dicoding.asclepius.repository.HistoryRepository
import kotlinx.coroutines.launch

class ResultViewModel(application: Application) : AndroidViewModel(application) {
    private val historyRepository = HistoryRepository(application)

    fun insertHistory(history: HistoryClassification) = viewModelScope.launch {
        historyRepository.insertHistory(history)
    }

    fun deleteHistory(history: HistoryClassification) = viewModelScope.launch {
        historyRepository.deleteHistory(history)
    }

    fun getAllHistory() = historyRepository.getAllHistory()
}