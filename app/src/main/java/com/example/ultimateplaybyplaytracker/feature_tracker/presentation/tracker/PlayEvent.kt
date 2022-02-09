package com.example.ultimateplaybyplaytracker.feature_tracker.presentation.tracker

import com.example.ultimateplaybyplaytracker.feature_tracker.domain.model.Play

sealed class PlayEvent {
    data class revertPlay(val play: Play):PlayEvent()
    object exportPlays:PlayEvent()
    data class logPlay(val logItem: String):PlayEvent()
}
