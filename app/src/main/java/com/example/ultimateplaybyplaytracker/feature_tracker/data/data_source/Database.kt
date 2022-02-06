package com.example.ultimateplaybyplaytracker.feature_tracker.data.data_source

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.ultimateplaybyplaytracker.feature_tracker.domain.model.Play
import com.example.ultimateplaybyplaytracker.feature_tracker.domain.model.Player

@Database(
    entities = [Player::class, Play::class], version=1
)
abstract class PlayerDatabase: RoomDatabase() {

    abstract val playerDao: PlayerDao
    abstract val playDao: PlayDao

    companion object {
        const val DATABASE_NAME = "players_db"
    }
}