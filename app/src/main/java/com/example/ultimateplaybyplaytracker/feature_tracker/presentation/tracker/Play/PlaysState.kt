package com.example.ultimateplaybyplaytracker.feature_tracker.presentation.tracker.Play

import com.example.ultimateplaybyplaytracker.feature_tracker.domain.model.Play
import kotlinx.serialization.Serializable

@Serializable
data class PlaysState (val plays: List<Play> = emptyList())