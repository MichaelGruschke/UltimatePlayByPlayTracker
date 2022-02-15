package com.example.ultimateplaybyplaytracker.feature_tracker.domain.model

import com.example.ultimateplaybyplaytracker.feature_tracker.data.services.csv.Exportable
import com.opencsv.bean.CsvBindByName



data class PlayCSV(
    @CsvBindByName(column = "timestamp")
    val timestamp: String,
    @CsvBindByName(column = "event")
    val event: String,
) : Exportable

fun List<Play>.toCsv() : List<PlayCSV> = map {
    PlayCSV(
        timestamp = it.timestamp,
        event = it.event,
    )
}