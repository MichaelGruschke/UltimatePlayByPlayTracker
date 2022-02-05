package com.example.ultimateplaybyplaytracker.feature_tracker.presentation.tracker

import com.example.ultimateplaybyplaytracker.feature_tracker.domain.model.Player

data class TrackerState (val players: List<Player> = emptyList())

