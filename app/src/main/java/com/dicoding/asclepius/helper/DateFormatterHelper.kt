package com.dicoding.asclepius.helper

import android.annotation.SuppressLint
import android.icu.text.SimpleDateFormat
import java.util.Date

class DateFormatterHelper {

    companion object {
        @SuppressLint("SimpleDateFormat")
        fun formatDate(date: Date): String {
            val sdf = SimpleDateFormat("dd/MM/yyyy")
            return sdf.format(date)
        }
    }
}