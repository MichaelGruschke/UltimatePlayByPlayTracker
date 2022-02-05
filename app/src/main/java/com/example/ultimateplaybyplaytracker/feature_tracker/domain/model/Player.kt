package com.example.ultimateplaybyplaytracker.feature_tracker.domain.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import org.jetbrains.annotations.PropertyKey

@Entity
data class Player(

    @PrimaryKey val id: Int,
    val name: String,
)
