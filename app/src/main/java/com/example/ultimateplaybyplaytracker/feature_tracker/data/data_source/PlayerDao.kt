package com.example.ultimateplaybyplaytracker.feature_tracker.data.data_source

import androidx.room.*
import com.example.ultimateplaybyplaytracker.feature_tracker.domain.model.Player
import kotlinx.coroutines.flow.Flow


@Dao
interface PlayerDao {

    @Query("SELECT * FROM Player")
    fun getPlayers(): Flow<List<Player>>

    @Query("SELECT * FROM Player WHERE id= :id")
    suspend fun getPlayerById(id: Int): Player?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPlayer(player: Player)

    @Delete
    suspend fun deletePlayer(player: Player)
}