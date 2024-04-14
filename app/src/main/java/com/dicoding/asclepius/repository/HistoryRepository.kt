package com.dicoding.asclepius.repository

import android.content.Context
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.dicoding.asclepius.BuildConfig
import com.dicoding.asclepius.data.local.entity.HistoryClassification
import com.dicoding.asclepius.data.local.room.HistoryClassificationDatabase
import com.dicoding.asclepius.data.remote.response.NewsArticleResponse
import com.dicoding.asclepius.data.remote.retrofit.ApiConfig
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HistoryRepository(context: Context) {
    private val db: HistoryClassificationDatabase = HistoryClassificationDatabase.getDatabaseInstance(context)

    private val _history = MutableLiveData<List<HistoryClassification>>()
    val history : LiveData<List<HistoryClassification>> = _history

    suspend fun insertHistory(history: HistoryClassification) {
        db.historyClassificationDao().insertHistory(history)
    }

    suspend fun deleteHistory(history: HistoryClassification) {
        db.historyClassificationDao().deleteHistory(history)
    }

    fun getAllHistory() = db.historyClassificationDao().getAllHistory()
}