package com.example.ultimateplaybyplaytracker.feature_tracker.presentation.tracker.Play

import com.example.ultimateplaybyplaytracker.feature_tracker.domain.model.Play
import com.example.ultimateplaybyplaytracker.feature_tracker.domain.model.Player

sealed class PlayEvent {
    data class RevertPlay(val play: Play): PlayEvent()
    object ExportPlays: PlayEvent()
    data class LogPlay(val logItem: String, val isO: Boolean, val line: String): PlayEvent()
    object DeletaAll: PlayEvent()
}
