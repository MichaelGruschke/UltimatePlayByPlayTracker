package com.example.ultimateplaybyplaytracker.feature_tracker.presentation.addPlayer

import androidx.compose.ui.focus.FocusState

sealed class AddPlayerEvent {
    data class EnteredName(val value: String) : AddPlayerEvent()
    data class ChangeTitleFocus(val focusState: FocusState) : AddPlayerEvent()
    object SavePlayer : AddPlayerEvent()
    data class SetCheckbox(val isChecked: Boolean) : AddPlayerEvent()
}
