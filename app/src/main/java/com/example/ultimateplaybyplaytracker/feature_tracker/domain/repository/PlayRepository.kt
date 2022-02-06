package com.example.ultimateplaybyplaytracker.feature_tracker.domain.repository

import com.example.ultimateplaybyplaytracker.feature_tracker.domain.model.Play
import kotlinx.coroutines.flow.Flow

interface PlayRepository {

    fun getPlays(): Flow<List<Play>>

    fun getMostRecentPlays(amount: Int): Flow<List<Play>>

    suspend fun insertPlay(play: Play)

    suspend fun deletePlay(play: Play)

    suspend fun deleteAll()
}