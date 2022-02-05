package com.example.ultimateplaybyplaytracker.feature_tracker.presentation.utils

sealed class Screen (val route: String) {
    object TrackerScreen: Screen("tracker_screen")
    object AddPlayerScreen: Screen("add_player_screen")
}