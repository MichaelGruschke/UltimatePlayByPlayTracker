package com.example.ultimateplaybyplaytracker.feature_tracker.domain.use_case.player

import com.example.ultimateplaybyplaytracker.feature_tracker.domain.model.InvalidPlayerException
import com.example.ultimateplaybyplaytracker.feature_tracker.domain.model.Player
import com.example.ultimateplaybyplaytracker.feature_tracker.domain.repository.PlayerRepository

class AddPlayer(private val repository: PlayerRepository) {

    @Throws(InvalidPlayerException::class)
    suspend operator fun invoke(player: Player){
        if (player.name.isBlank()) {
            throw InvalidPlayerException("Player name cannot be empty!")
        }
        repository.insertPlayer(player)
    }
}