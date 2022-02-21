package com.example.ultimateplaybyplaytracker.feature_tracker.domain.use_case.logger

import com.example.ultimateplaybyplaytracker.feature_tracker.domain.repository.PlayRepository

class DeleteAll (private val repository: PlayRepository) {
    suspend operator fun invoke(){
        repository.deleteAll()
    }
}