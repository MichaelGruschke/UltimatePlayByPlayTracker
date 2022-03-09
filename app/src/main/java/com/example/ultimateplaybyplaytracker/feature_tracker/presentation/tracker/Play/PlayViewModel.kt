package com.example.ultimateplaybyplaytracker.feature_tracker.presentation.tracker.Play

import android.app.Application
import android.content.ContentValues
import android.provider.MediaStore
import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.viewModelScope
import com.example.ultimateplaybyplaytracker.feature_tracker.data.services.csv.ExportConfig
import com.example.ultimateplaybyplaytracker.feature_tracker.domain.model.Play
import com.example.ultimateplaybyplaytracker.feature_tracker.domain.use_case.logger.PlayUseCases
import androidx.lifecycle.AndroidViewModel
import com.example.ultimateplaybyplaytracker.feature_tracker.domain.model.Player
import com.example.ultimateplaybyplaytracker.feature_tracker.presentation.tracker.player.LineupState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import java.io.IOException
import java.io.OutputStreamWriter
import java.text.SimpleDateFormat
import javax.inject.Inject

@HiltViewModel
class PlayViewModel @Inject constructor(
    private val playUseCases: PlayUseCases, application: Application,
) :
    AndroidViewModel(application) {

    private val _state = mutableStateOf(PlaysState())
    val state: State<PlaysState> = _state

    private val _lineup = mutableStateOf(LineupState())
    val lineup: State<LineupState> = _lineup

    private val _trackerState = mutableStateOf(TrackerState())
    val trackerState: State<TrackerState> = _trackerState

    private var playsJob: Job? = null

    private val _eventFlow = MutableSharedFlow<TrackerUiEvent>()
    val eventFlow = _eventFlow.asSharedFlow()

    init {
        getPlays()
    }

    fun onEvent(event: PlayEvent) {
        when (event) {
            is PlayEvent.RevertPlay -> {
                viewModelScope.launch {
                    playUseCases.deletePlay(event.play)
                }
            }
            is PlayEvent.ExportPlays -> {
                writeJson()
            }
            is PlayEvent.LogPlay -> {
                viewModelScope.launch {
                    playUseCases.logPlay(
                        Play(
                            timestamp = SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
                                .format(System.currentTimeMillis())
                                .toString(),
                            event = event.logItem,
                            isO = event.isO,
                            line = event.line
                        )
                    )
                }
            }
            is PlayEvent.DeletaAll -> {
                viewModelScope.launch {
                    playUseCases.deleteAll()
                    _eventFlow.emit(TrackerUiEvent.ShowSnackbar("Play by Play Log cleared"))
                }
            }
            is PlayEvent.ModifyPlayerLineup -> {
                modifyLineup(event.player)
            }
            is PlayEvent.ToggleEditMode -> {
                _trackerState.value = trackerState.value.copy(
                    isEditMode = !trackerState.value.isEditMode
                )
            }
            is PlayEvent.ToggleLineSelectionMode -> {
                _trackerState.value = trackerState.value.copy(
                    isLineSelectionMode = !trackerState.value.isLineSelectionMode
                )
            }
            is PlayEvent.SetOLineFlag -> {
                _trackerState.value = trackerState.value.copy(
                    isOLine = event.isOLine
                )
            }

        }
    }

    private fun getPlays() {
        playsJob?.cancel()
        playsJob = playUseCases.getPlays().onEach { plays ->
            _state.value = state.value.copy(plays = plays)
        }.launchIn(viewModelScope)
    }

    private fun writeJson(){
        viewModelScope.launch {
            Log.d("EXPORT", Json.encodeToString(state.value))
            val exportConfig = ExportConfig()
            val contentValue = ContentValues().apply {
                put(MediaStore.Downloads.DISPLAY_NAME, exportConfig.fileName)
                put(MediaStore.Downloads.MIME_TYPE, "text/plain")
            }
            val context = getApplication<Application>().applicationContext
            try {
                context.contentResolver.insert(exportConfig.uri, contentValue)
                    ?.also { uri ->
                        context.contentResolver.openOutputStream(uri).use { outputStream ->
                            val outputWriter = OutputStreamWriter(outputStream)
                            outputWriter.write(Json.encodeToString(state.value))
                            outputWriter.close()
                        }
                    }
                _eventFlow.emit(TrackerUiEvent.ShowSnackbar("Play by Play Log saved successfully"))
            } catch (e: IOException) {
                _eventFlow.emit(TrackerUiEvent.ShowSnackbar("Play by Play Log cannot be saved"))
            }
        }
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

    sealed class TrackerUiEvent {
        data class ShowSnackbar(val message: String) : TrackerUiEvent()
    }
}

