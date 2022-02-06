package com.example.ultimateplaybyplaytracker.feature_tracker.data.repository

import com.example.ultimateplaybyplaytracker.feature_tracker.data.data_source.PlayDao
import com.example.ultimateplaybyplaytracker.feature_tracker.domain.model.Play
import com.example.ultimateplaybyplaytracker.feature_tracker.domain.repository.PlayRepository

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.map

class PlayRepositoryImpl(private val dao: PlayDao) : PlayRepository {

    override fun getPlays(): Flow<List<Play>> {
        return dao.getPlays()
    }

    override fun getMostRecentPlays(amount: Int): Flow<List<Play>> {
        return dao.getPlays().map { players -> players.takeLast(amount) }
    }

    override suspend fun insertPlay(play: Play) {
        dao.insertPlay(play)
    }

    override suspend fun deletePlay(play: Play) {
        dao.deletePlay(play)
    }

    override suspend fun deleteAll() {
        dao.deleteAll()
    }

}