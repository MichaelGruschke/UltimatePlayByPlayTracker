package com.example.ultimateplaybyplaytracker.feature_tracker.domain.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import org.jetbrains.annotations.PropertyKey

@Entity
data class Player(

    @PrimaryKey val id: String,
    val name: String,
)

class InvalidPlayerException(message: String): Exception(message)