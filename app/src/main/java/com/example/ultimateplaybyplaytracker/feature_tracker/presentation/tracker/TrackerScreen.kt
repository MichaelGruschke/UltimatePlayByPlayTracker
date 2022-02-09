package com.example.ultimateplaybyplaytracker.feature_tracker.presentation.tracker


import android.annotation.SuppressLint
import android.os.Build
import androidx.annotation.RequiresApi

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.ultimateplaybyplaytracker.feature_tracker.domain.model.Play
import com.example.ultimateplaybyplaytracker.feature_tracker.presentation.tracker.components.PlayerItem
import com.example.ultimateplaybyplaytracker.feature_tracker.presentation.utils.Screen


@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalFoundationApi::class)
@Composable
fun TrackerScreen(
    navController: NavController,
    playerViewModel: PlayerViewModel = hiltViewModel(),
    playViewModel: PlayViewModel = hiltViewModel()
) {
    val playerState = playerViewModel.state.value
    val playState = playViewModel.state.value
    val scaffoldState = rememberScaffoldState()

    val lastPlay: Play? = null

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
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color.White)
            ) {
                Text(
                    text = playState.plays.takeLast(5).map { item ->
                        item.event
                    }.joinToString(">"),
                    color = Color.Black
                )


                OutlinedButton(onClick = {

                }) {
                    Text("Undo")
                }
            }


            LazyVerticalGrid(
                modifier = Modifier.fillMaxSize(),
                cells = GridCells.Adaptive(128.dp)
            ) {
                items(playerState.players) { player ->
                    PlayerItem(
                        player = player,
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable {
                                playViewModel.onEvent(PlayEvent.logPlay(player.name))
                            })
                }
            }
        }


    }

}



