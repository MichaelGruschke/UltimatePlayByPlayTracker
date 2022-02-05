package com.example.ultimateplaybyplaytracker.feature_tracker.presentation.tracker

import androidx.lifecycle.ViewModel
import com.example.ultimateplaybyplaytracker.feature_tracker.domain.use_case.PlayerUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class TrackerViewModel @Inject constructor(private val playerUseCases: PlayerUseCases) :
    ViewModel() {

        fun onEvent(event: PlayersEvent){
            when(event) {
                is PlayersEvent.DeletePlayer -> {

                }
            }
        }
}