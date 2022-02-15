package com.example.ultimateplaybyplaytracker.feature_tracker.presentation.tracker

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.ultimateplaybyplaytracker.feature_tracker.data.services.csv.CsvConfig
import com.example.ultimateplaybyplaytracker.feature_tracker.data.services.csv.ExportService
import com.example.ultimateplaybyplaytracker.feature_tracker.data.services.csv.Exports
import com.example.ultimateplaybyplaytracker.feature_tracker.domain.model.Play
import com.example.ultimateplaybyplaytracker.feature_tracker.domain.model.PlayCSV
import com.example.ultimateplaybyplaytracker.feature_tracker.domain.model.toCsv
import com.example.ultimateplaybyplaytracker.feature_tracker.domain.use_case.logger.PlayUseCases

import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import javax.inject.Inject

@HiltViewModel
class PlayViewModel @Inject constructor(
    private val playUseCases: PlayUseCases,
    private val exportService: ExportService
) :
    ViewModel() {

    private val _state = mutableStateOf(PlaysState())
    val state: State<PlaysState> = _state

    private var playsJob: Job? = null

    private val _eventFlow = MutableSharedFlow<TrackerUiEvent>()
    val eventFlow = _eventFlow.asSharedFlow()

    init {
        getPlays()
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun onEvent(event: PlayEvent) {
        when (event) {
            is PlayEvent.RevertPlay -> {
                viewModelScope.launch {
                    playUseCases.deletePlay(event.play)
                }
            }
            is PlayEvent.ExportPlays -> {
                viewModelScope.launch(IO) {
                    val transactions = state.value.copy().plays
                    exportService.export<PlayCSV>(
                        type = Exports.CSV(CsvConfig()), // 👈 apply config + type of export
                        content = transactions.toCsv() // 👈 send transformed data of exportable type
                    ).catch { error ->
                        _eventFlow.emit(TrackerUiEvent.ShowSnackbar(error.toString()))
                    }.collect { _ ->
                        // 👇 do anything on success
                        _eventFlow.emit(TrackerUiEvent.ShowSnackbar("Plays saved successfully!"))
                    }
                }
            }
            is PlayEvent.LogPlay -> {
                viewModelScope.launch {

                    playUseCases.logPlay(
                        Play(
                            timestamp = SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
                                .format(System.currentTimeMillis())
                                .toString(),
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

    sealed class TrackerUiEvent {
        data class ShowSnackbar(val message: String) : TrackerUiEvent()
    }
}

