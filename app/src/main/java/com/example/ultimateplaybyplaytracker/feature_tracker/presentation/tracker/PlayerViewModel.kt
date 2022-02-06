package com.example.ultimateplaybyplaytracker.feature_tracker.presentation.tracker

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.ultimateplaybyplaytracker.feature_tracker.domain.use_case.player.PlayerUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PlayerViewModel @Inject constructor(private val playerUseCases: PlayerUseCases) :
    ViewModel() {

    private val _state = mutableStateOf(PlayersState())
    val state: State<PlayersState> = _state

    private var playersJob: Job? = null

    init {
        getPlayers()
    }

    fun onEvent(event: TrackerEvent) {
        when (event) {
            is TrackerEvent.DeletePlayer -> {
                viewModelScope.launch {
                    playerUseCases.deletePlayer(event.player)
                }
            }
        }
    }

    private fun getPlayers() {
        playersJob?.cancel()
        playersJob = playerUseCases.getPlayers().onEach { players ->
            _state.value = state.value.copy(players = players)
        }.launchIn(viewModelScope)
    }
}
