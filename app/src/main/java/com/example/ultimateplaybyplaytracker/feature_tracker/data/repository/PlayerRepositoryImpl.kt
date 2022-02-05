package com.example.ultimateplaybyplaytracker.feature_tracker.data.repository

import com.example.ultimateplaybyplaytracker.feature_tracker.data.data_source.PlayerDao
import com.example.ultimateplaybyplaytracker.feature_tracker.domain.model.Player
import com.example.ultimateplaybyplaytracker.feature_tracker.domain.repository.PlayerRepository
import kotlinx.coroutines.flow.Flow

class PlayerRepositoryImpl(private val dao: PlayerDao) : PlayerRepository {
    override fun getPlayers(): Flow<List<Player>> {
        return dao.getPlayers()
    }

    override suspend fun getPlayerById(id: Int): Player? {
        return dao.getPlayerById(id)
    }

    override suspend fun insertPlayer(player: Player) {
        dao.insertPlayer(player)
    }

    override suspend fun deletePlayer(player: Player) {
        dao.deletePlayer(player)
    }
}