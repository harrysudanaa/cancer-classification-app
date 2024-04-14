package com.dicoding.asclepius.data.local.room

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.dicoding.asclepius.data.local.entity.HistoryClassification

@Dao
interface HistoryClassificationDao {
    @Insert
    suspend fun insertHistory(history: HistoryClassification)

    @Delete
    suspend fun deleteHistory(history: HistoryClassification)

    @Query("SELECT * FROM history_classification")
    fun getAllHistory() : LiveData<List<HistoryClassification>>
}