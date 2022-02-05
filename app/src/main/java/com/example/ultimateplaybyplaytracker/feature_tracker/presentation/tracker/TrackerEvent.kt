package com.example.ultimateplaybyplaytracker.feature_tracker.presentation.tracker

import com.example.ultimateplaybyplaytracker.feature_tracker.domain.model.Player

sealed class TrackerEvent {
    data class DeletePlayer(val player: Player): TrackerEvent()
    data class AddPlayer(val player: Player): TrackerEvent()
}
