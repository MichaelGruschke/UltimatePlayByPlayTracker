package com.example.ultimateplaybyplaytracker.feature_tracker.data.data_source

import androidx.room.*
import com.example.ultimateplaybyplaytracker.feature_tracker.domain.model.Player
import kotlinx.coroutines.flow.Flow


@Dao
interface PlayerDao {

    @Query("SELECT * FROM player")
    fun getPlayers(): Flow<List<Player>>

    @Delete
    suspend fun deletePlayer(player: Player)
}