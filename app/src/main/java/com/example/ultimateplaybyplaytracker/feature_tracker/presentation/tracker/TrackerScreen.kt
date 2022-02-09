package com.example.ultimateplaybyplaytracker.feature_tracker.presentation.tracker


import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.GridCells
import androidx.compose.foundation.lazy.LazyVerticalGrid
import androidx.compose.foundation.lazy.items

import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.ultimateplaybyplaytracker.feature_tracker.presentation.tracker.components.PlayerItem
import com.example.ultimateplaybyplaytracker.feature_tracker.presentation.utils.Screen


@OptIn(ExperimentalFoundationApi::class)
@Composable
fun TrackerScreen(
    navController: NavController,
    viewModel: PlayerViewModel = hiltViewModel()
) {
    val state = viewModel.state.value
    val scaffoldState = rememberScaffoldState()

    Scaffold(
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    navController.navigate(Screen.AddPlayerScreen.route)
                },
                backgroundColor = MaterialTheme.colors.primary
            ) {
                Icon(imageVector = Icons.Default.Add, contentDescription = "Add note")
            }
        },
        scaffoldState = scaffoldState
    ) {
        Column(modifier = Modifier.fillMaxSize()) {
            Text(text = "PLACEHOLDER")
        }
        
        LazyVerticalGrid(modifier = Modifier.fillMaxSize(),
        cells = GridCells.Adaptive(128.dp)) {
            items(state.players) { player ->
                PlayerItem(
                    player = player,
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable {
                            viewModel.onEvent(PlayerEvent.DeletePlayer(player))
                        })
            }
        }
    }

}



