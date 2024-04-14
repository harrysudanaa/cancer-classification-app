package com.dicoding.asclepius.data.local.entity

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Entity(tableName = "history_classification")
@Parcelize
data class HistoryClassification(
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0,
    var label: String = "",
    var score: Float = 0f,
    var imageData: ByteArray,
    var date: String
) : Parcelable
