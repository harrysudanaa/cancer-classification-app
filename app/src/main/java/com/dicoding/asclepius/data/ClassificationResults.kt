package com.dicoding.asclepius.data

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class ClassificationResults(
    var label: String = "",
    var score: Float = 0f
) : Parcelable
