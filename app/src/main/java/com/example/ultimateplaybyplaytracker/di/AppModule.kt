package com.example.ultimateplaybyplaytracker.di

import android.app.Application
import androidx.room.Room
import com.example.ultimateplaybyplaytracker.feature_tracker.data.data_source.TrackerDatabase
import com.example.ultimateplaybyplaytracker.feature_tracker.data.repository.PlayRepositoryImpl
import com.example.ultimateplaybyplaytracker.feature_tracker.data.repository.PlayerRepositoryImpl
import com.example.ultimateplaybyplaytracker.feature_tracker.data.services.csv.ExportService
import com.example.ultimateplaybyplaytracker.feature_tracker.domain.repository.PlayRepository
import com.example.ultimateplaybyplaytracker.feature_tracker.domain.repository.PlayerRepository
import com.example.ultimateplaybyplaytracker.feature_tracker.domain.use_case.logger.DeletePlay
import com.example.ultimateplaybyplaytracker.feature_tracker.domain.use_case.logger.GetPlays
import com.example.ultimateplaybyplaytracker.feature_tracker.domain.use_case.logger.LogPlay
import com.example.ultimateplaybyplaytracker.feature_tracker.domain.use_case.logger.PlayUseCases
import com.example.ultimateplaybyplaytracker.feature_tracker.domain.use_case.player.AddPlayer
import com.example.ultimateplaybyplaytracker.feature_tracker.domain.use_case.player.DeletePlayer
import com.example.ultimateplaybyplaytracker.feature_tracker.domain.use_case.player.GetPlayers
import com.example.ultimateplaybyplaytracker.feature_tracker.domain.use_case.player.PlayerUseCases
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
    fun provideTrackerDatabase(app: Application): TrackerDatabase {
        return Room.databaseBuilder(app, TrackerDatabase::class.java, TrackerDatabase.DATABASE_NAME)
            .build()
    }

    @Provides
    @Singleton
    fun providePlayerRepository(db: TrackerDatabase) : PlayerRepository {
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

    @Provides
    @Singleton
    fun providePlayRepository(db: TrackerDatabase) : PlayRepository {
        return PlayRepositoryImpl(db.playDao)
    }

    @Provides
    @Singleton
    fun providePlayUseCases(repository: PlayRepository): PlayUseCases {
        return PlayUseCases(
            logPlay = LogPlay(repository),
            getPlays = GetPlays(repository),
            deletePlay = DeletePlay(repository)
        )
    }

    @Provides
    @Singleton
    fun provideExportService(): ExportService {
        return ExportService()
    }
}