package com.example.ultimateplaybyplaytracker.feature_tracker.domain.use_case.player

import com.example.ultimateplaybyplaytracker.feature_tracker.domain.model.Player
import com.example.ultimateplaybyplaytracker.feature_tracker.domain.repository.PlayerRepository

class DeletePlayer(private val repository: PlayerRepository) {
    suspend operator fun invoke(player: Player){
        repository.deletePlayer(player)
    }
}