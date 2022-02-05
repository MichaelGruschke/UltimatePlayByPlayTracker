package com.example.ultimateplaybyplaytracker.feature_tracker.domain.use_case

import com.example.ultimateplaybyplaytracker.feature_tracker.domain.model.Player
import com.example.ultimateplaybyplaytracker.feature_tracker.domain.repository.PlayerRepository
import kotlinx.coroutines.flow.Flow

class GetPlayers(private val repository: PlayerRepository) {
    operator fun invoke(): Flow<List<Player>> {
        return repository.getPlayers()
    }
}