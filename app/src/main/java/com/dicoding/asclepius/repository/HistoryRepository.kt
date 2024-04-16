package com.dicoding.asclepius.repository

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.dicoding.asclepius.data.local.entity.HistoryClassification
import com.dicoding.asclepius.data.local.room.HistoryClassificationDatabase

class HistoryRepository(context: Context) {
    private val db: HistoryClassificationDatabase =
        HistoryClassificationDatabase.getDatabaseInstance(context)

    private val _history = MutableLiveData<List<HistoryClassification>>()
    val history: LiveData<List<HistoryClassification>> = _history

    suspend fun insertHistory(history: HistoryClassification) {
        db.historyClassificationDao().insertHistory(history)
    }

    fun getAllHistory() = db.historyClassificationDao().getAllHistory()
}