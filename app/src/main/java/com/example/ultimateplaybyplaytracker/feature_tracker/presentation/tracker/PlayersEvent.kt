package com.example.ultimateplaybyplaytracker.feature_tracker.presentation.tracker

import com.example.ultimateplaybyplaytracker.feature_tracker.domain.model.Player

sealed class PlayersEvent {
    data class DeletePlayer(val player: Player): PlayersEvent()
    data class AddPlayer(val player: Player): PlayersEvent()
}
