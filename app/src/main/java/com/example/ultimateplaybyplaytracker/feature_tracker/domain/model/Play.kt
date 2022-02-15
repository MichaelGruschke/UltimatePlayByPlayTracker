package com.example.ultimateplaybyplaytracker.feature_tracker.domain.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Play(
    @PrimaryKey val timestamp: String,
    val event: String,
)


