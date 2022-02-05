package com.example.ultimateplaybyplaytracker.feature_tracker.presentation

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.ultimateplaybyplaytracker.R
import com.example.ultimateplaybyplaytracker.feature_tracker.presentation.tracker.AddPlayerScreen
import com.example.ultimateplaybyplaytracker.feature_tracker.presentation.tracker.TrackerScreen
import com.example.ultimateplaybyplaytracker.feature_tracker.presentation.utils.Screen
import com.example.ultimateplaybyplaytracker.ui.theme.CleanArchitectureNoteAppTheme

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            CleanArchitectureNoteAppTheme {
                Surface(
                    color = MaterialTheme.colors.background
                ){
                    val navController = rememberNavController()
                    NavHost(navController = navController,
                        startDestination = Screen.TrackerScreen.route
                    ){
                        composable(route =  Screen.TrackerScreen.route) {
                            TrackerScreen(navController=navController)
                        }
                        composable(route = Screen.AddPlayerScreen.route) {
                            AddPlayerScreen(navController = navController)
                        }
                    }
                }
            }
        }
    }
}