package com.example.ultimateplaybyplaytracker.feature_tracker.presentation.tracker

import com.example.ultimateplaybyplaytracker.feature_tracker.domain.model.Play

sealed class PlayEvent {
    data class RevertPlay(val play: Play):PlayEvent()
    object ExportPlays:PlayEvent()
    data class LogPlay(val logItem: String):PlayEvent()
}
