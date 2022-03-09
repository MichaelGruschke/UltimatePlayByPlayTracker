package com.example.ultimateplaybyplaytracker.feature_tracker.presentation.tracker.Play

import com.example.ultimateplaybyplaytracker.feature_tracker.domain.model.Play
import com.example.ultimateplaybyplaytracker.feature_tracker.domain.model.Player
import com.example.ultimateplaybyplaytracker.feature_tracker.presentation.tracker.player.PlayerEvent

sealed class PlayEvent {
    data class RevertPlay(val play: Play): PlayEvent()
    object ExportPlays: PlayEvent()
    data class LogPlay(val logItem: String): PlayEvent()
    object DeletaAll: PlayEvent()
    data class ModifyPlayerLineup(val player: Player): PlayEvent()
    object ToggleEditMode: PlayEvent()
    object ToggleLineSelectionMode: PlayEvent()
    data class SetOLineFlag(val isOLine: Boolean): PlayEvent()
}
