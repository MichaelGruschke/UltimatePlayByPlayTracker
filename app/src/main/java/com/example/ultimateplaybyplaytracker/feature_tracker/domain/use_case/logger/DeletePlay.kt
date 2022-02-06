package com.example.ultimateplaybyplaytracker.feature_tracker.domain.use_case.logger

import com.example.ultimateplaybyplaytracker.feature_tracker.domain.model.Play
import com.example.ultimateplaybyplaytracker.feature_tracker.domain.repository.PlayRepository

class DeletePlay (private val repository: PlayRepository) {
    suspend operator fun invoke(play: Play){
        repository.deletePlay(play)
    }
}