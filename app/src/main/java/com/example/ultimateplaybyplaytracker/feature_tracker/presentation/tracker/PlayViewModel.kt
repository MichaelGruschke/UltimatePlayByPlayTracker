package com.example.ultimateplaybyplaytracker.feature_tracker.presentation.tracker

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.ultimateplaybyplaytracker.feature_tracker.domain.use_case.logger.PlayUseCases

import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PlayViewModel @Inject constructor(private val playUseCases: PlayUseCases) :
    ViewModel() {

    private val _state = mutableStateOf(PlaysState())
    val state: State<PlaysState> = _state

    fun onEvent(event: PlayEvent) {
        when (event) {
            is PlayEvent.RevertPlay -> {
                TODO()
            }
            is PlayEvent.exportPlays -> {
                TODO()
            }
            is PlayEvent.logPlay -> {
                TODO()
            }
        }
    }
}

