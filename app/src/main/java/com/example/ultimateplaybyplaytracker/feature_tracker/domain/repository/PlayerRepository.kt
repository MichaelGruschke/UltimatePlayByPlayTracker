package com.example.ultimateplaybyplaytracker.feature_tracker.domain.repository
import com.example.ultimateplaybyplaytracker.feature_tracker.domain.model.Player
import kotlinx.coroutines.flow.Flow

interface PlayerRepository {

    fun getPlayers(): Flow<List<Player>>

    suspend fun getPlayerById(id: Int): Player?

    suspend fun insertPlayer(player: Player)

    suspend fun deletePlayer(player: Player)
}