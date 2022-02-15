package com.example.ultimateplaybyplaytracker.feature_tracker.data.services.csv

import android.os.Environment
import java.text.SimpleDateFormat

data class CsvConfig(
    private val prefix: String = "PlayByPlay",
    private val suffix: String = SimpleDateFormat("yyyy-MM-dd")
                                    .format(System.currentTimeMillis())
                                    .toString(),

    val fileName: String = "$prefix-$suffix.csv",
    @Suppress("DEPRECATION")
    val hostPath: String = Environment.getExternalStorageDirectory().absolutePath
)