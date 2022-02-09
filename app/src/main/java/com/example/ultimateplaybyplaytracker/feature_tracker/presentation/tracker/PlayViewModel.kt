package com.example.ultimateplaybyplaytracker.feature_tracker.presentation.tracker

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.ultimateplaybyplaytracker.feature_tracker.domain.model.Play
import com.example.ultimateplaybyplaytracker.feature_tracker.domain.model.Player
import com.example.ultimateplaybyplaytracker.feature_tracker.domain.use_case.logger.DeletePlay
import com.example.ultimateplaybyplaytracker.feature_tracker.domain.use_case.logger.PlayUseCases

import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import java.time.LocalDateTime
import javax.inject.Inject
import kotlin.random.Random

@HiltViewModel
class PlayViewModel @Inject constructor(private val playUseCases: PlayUseCases) :
    ViewModel() {

    private val _state = mutableStateOf(PlaysState())
    val state: State<PlaysState> = _state

    private var playsJob: Job? = null

    init {
        getPlays()
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun onEvent(event: PlayEvent) {
        when (event) {
            is PlayEvent.revertPlay -> {
                viewModelScope.launch {
                    playUseCases.deletePlay(event.play)
                }
            }
            is PlayEvent.exportPlays -> {
                TODO()
            }
            is PlayEvent.logPlay -> {
                viewModelScope.launch {

                    playUseCases.logPlay(
                        Play(
                            timestamp = LocalDateTime.now().toString(),
                            event = event.logItem
                        )
                    )
                }
            }
        }
    }
    private fun getPlays() {
        playsJob?.cancel()
        playsJob = playUseCases.getPlays().onEach { plays ->
            _state.value = state.value.copy(plays = plays)
        }.launchIn(viewModelScope)
    }
}

