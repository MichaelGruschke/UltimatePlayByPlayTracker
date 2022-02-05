package com.example.ultimateplaybyplaytracker.feature_tracker.domain.use_case

import com.example.ultimateplaybyplaytracker.feature_tracker.domain.model.Player
import com.example.ultimateplaybyplaytracker.feature_tracker.domain.repository.PlayerRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class GetPlayers(private val repository: PlayerRepository) {
    operator fun invoke(): Flow<List<Player>> {
        return repository.getPlayers().map { players -> players.sortedBy { it.name } }
    }
}