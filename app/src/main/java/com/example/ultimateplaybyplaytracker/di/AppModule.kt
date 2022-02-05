package com.example.ultimateplaybyplaytracker.di

import android.app.Application
import androidx.room.Room
import com.example.ultimateplaybyplaytracker.feature_tracker.data.data_source.PlayerDatabase
import com.example.ultimateplaybyplaytracker.feature_tracker.data.repository.PlayerRepositoryImpl
import com.example.ultimateplaybyplaytracker.feature_tracker.domain.repository.PlayerRepository
import com.example.ultimateplaybyplaytracker.feature_tracker.domain.use_case.AddPlayer
import com.example.ultimateplaybyplaytracker.feature_tracker.domain.use_case.DeletePlayer
import com.example.ultimateplaybyplaytracker.feature_tracker.domain.use_case.GetPlayers
import com.example.ultimateplaybyplaytracker.feature_tracker.domain.use_case.PlayerUseCases
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun providePlayerDatabase(app: Application): PlayerDatabase {
        return Room.databaseBuilder(app, PlayerDatabase::class.java, PlayerDatabase.DATABASE_NAME)
            .build()
    }

    @Provides
    @Singleton
    fun providePlayerRepository(db: PlayerDatabase) : PlayerRepository {
        return PlayerRepositoryImpl(db.playerDao)
    }

    @Provides
    @Singleton
    fun providePlayerUseCases(repository: PlayerRepository): PlayerUseCases {
        return PlayerUseCases(
            getPlayers = GetPlayers(repository),
            deletePlayer = DeletePlayer(repository),
            addPlayer = AddPlayer(repository),
        )
    }
}