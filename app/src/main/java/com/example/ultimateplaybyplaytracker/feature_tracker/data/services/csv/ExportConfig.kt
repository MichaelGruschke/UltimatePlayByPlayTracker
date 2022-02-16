package com.example.ultimateplaybyplaytracker.feature_tracker.data.services.csv

import android.net.Uri
import android.provider.MediaStore
import java.text.SimpleDateFormat

data class ExportConfig  constructor(
    private val prefix: String = "PlayByPlay",
    private val suffix: String = SimpleDateFormat("yyyy-MM-dd")
                                    .format(System.currentTimeMillis())
                                    .toString(),

    val fileName: String = "$prefix-$suffix.txt",
    @Suppress("DEPRECATION")
    val uri: Uri = MediaStore.Downloads.EXTERNAL_CONTENT_URI
)