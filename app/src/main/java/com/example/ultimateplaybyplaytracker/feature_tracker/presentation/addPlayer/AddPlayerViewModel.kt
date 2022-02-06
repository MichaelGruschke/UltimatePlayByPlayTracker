package com.example.ultimateplaybyplaytracker.feature_tracker.presentation.addPlayer

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.ultimateplaybyplaytracker.feature_tracker.domain.model.InvalidPlayerException
import com.example.ultimateplaybyplaytracker.feature_tracker.domain.model.Player
import com.example.ultimateplaybyplaytracker.feature_tracker.domain.use_case.PlayerUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddPlayerViewModel @Inject constructor(
    private val playerUseCases: PlayerUseCases,
) : ViewModel() {

    private val _playerName = mutableStateOf(
        NameTextFieldState(
            hint = "Enter name"
        )
    )
    val playerName: State<NameTextFieldState> = _playerName

    private val _eventFlow = MutableSharedFlow<UiEvent>()
    val eventFlow = _eventFlow.asSharedFlow()

    fun onEvent(event: AddPlayerEvent) {
        when (event) {
            is AddPlayerEvent.EnteredName -> {
                _playerName.value = playerName.value.copy(
                    text = event.value
                )
            }
            is AddPlayerEvent.ChangeTitleFocus -> {
                _playerName.value = playerName.value.copy(
                    isHintVisisble = !event.focusState.isFocused && playerName.value.text.isBlank()
                )
            }
            is AddPlayerEvent.SavePlayer -> {
                viewModelScope.launch {
                    try {
                        playerUseCases.addPlayer(
                            Player(
                                id=1,
                                name = playerName.value.text
                            )
                        )
                        _eventFlow.emit(UiEvent.SaveNote)
                    } catch (e: InvalidPlayerException) {
                        _eventFlow.emit(
                            UiEvent.ShowSnackbar(
                                message = e.message ?: "Invalid Player"
                            )
                        )
                    }
                }
            }
        }
    }

    sealed class UiEvent {
        data class ShowSnackbar(val message: String) : UiEvent()
        object SaveNote : UiEvent()
    }
}