package com.example.ultimateplaybyplaytracker.feature_tracker.presentation.tracker

sealed class PlayEvent {
    object RevertPlay:PlayEvent()
    object exportPlays:PlayEvent()
    object logPlay:PlayEvent()
}
