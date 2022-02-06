package com.example.ultimateplaybyplaytracker.feature_tracker.data.data_source

import androidx.room.*
import com.example.ultimateplaybyplaytracker.feature_tracker.domain.model.Play
import kotlinx.coroutines.flow.Flow

@Dao
interface PlayDao {

    @Query("SELECT * FROM play")
    fun getPlays(): Flow<List<Play>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPlay(play: Play)

    @Delete
    suspend fun deletePlay(play: Play)

    @Query("DELETE FROM play")
    suspend fun deleteAll()
}