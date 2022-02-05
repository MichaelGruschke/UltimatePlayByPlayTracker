package com.example.ultimateplaybyplaytracker.feature_tracker.presentation.tracker

import androidx.compose.ui.focus.FocusState

sealed class AddPlayerEvent{
    data class EntreredTitle(val value: String): AddPlayerEvent()
    data class ChangeTitleFocus(val focusState: FocusState): AddPlayerEvent()
    object  SavePlayer: AddPlayerEvent()
}
