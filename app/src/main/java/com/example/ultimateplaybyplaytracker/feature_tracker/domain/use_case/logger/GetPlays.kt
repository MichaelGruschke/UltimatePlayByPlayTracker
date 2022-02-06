package com.example.ultimateplaybyplaytracker.feature_tracker.domain.use_case.logger

import com.example.ultimateplaybyplaytracker.feature_tracker.domain.model.Play
import com.example.ultimateplaybyplaytracker.feature_tracker.domain.repository.PlayRepository
import kotlinx.coroutines.flow.Flow

class GetPlays (private val repository: PlayRepository) {
    operator fun invoke(): Flow<List<Play>>  {
        return repository.getPlays()
    }
}