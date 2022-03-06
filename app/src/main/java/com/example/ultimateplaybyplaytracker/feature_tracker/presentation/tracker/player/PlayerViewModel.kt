package com.example.ultimateplaybyplaytracker.feature_tracker.presentation.tracker.player

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.ultimateplaybyplaytracker.feature_tracker.domain.model.Player
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

    private val _lineup = mutableStateOf(LineupState())
    val lineup: State<LineupState> = _lineup

    private var playersJob: Job? = null

    init {
        getPlayers()
    }

    fun onEvent(event: PlayerEvent) {
        when (event) {
            is PlayerEvent.DeletePlayer -> {
                viewModelScope.launch {
                    playerUseCases.deletePlayer(event.player)
                }
            }
            is PlayerEvent.ModifyPlayerLineup -> {
                modifyLineup(event.player)
            }

        }
    }

    private fun getPlayers() {
        playersJob?.cancel()
        playersJob = playerUseCases.getPlayers().onEach { players ->
            _state.value = state.value.copy(players = players)
        }.launchIn(viewModelScope)
    }

    private fun modifyLineup(player: Player){
        if (player in _lineup.value.players){
            _lineup.value = lineup.value.copy(players=lineup.value.players.filter { it != player})
        } else {
            if (_lineup.value.players.size >= 7) {
                _lineup.value = lineup.value.copy(players=lineup.value.players.drop(1))
            }
            _lineup.value = lineup.value.copy(players = lineup.value.players.plus(player))
        }
    }
}
