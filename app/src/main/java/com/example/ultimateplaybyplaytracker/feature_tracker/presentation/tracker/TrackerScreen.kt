package com.example.ultimateplaybyplaytracker.feature_tracker.presentation.tracker


import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.GridCells
import androidx.compose.foundation.lazy.LazyVerticalGrid
import androidx.compose.foundation.lazy.items

import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.ultimateplaybyplaytracker.feature_tracker.presentation.tracker.components.PlayerItem
import com.example.ultimateplaybyplaytracker.feature_tracker.presentation.utils.Screen
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.unit.dp


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

    val isEditMode = remember { mutableStateOf(false) }


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
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Top
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color.White),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Text(
                    text = playState.plays.takeLast(5).map { item ->
                        item.event
                    }.joinToString(" > "),
                    color = Color.Black
                )

                Button(
                    onClick = {
                        playViewModel.onEvent(PlayEvent.RevertPlay(playState.plays.last()))
                    }, colors = ButtonDefaults.buttonColors(
                        backgroundColor = Color.Red,
                        contentColor = MaterialTheme.colors.surface
                    ), enabled = playState.plays.isNotEmpty()
                ) {
                    Text(text = "Undo")
                }


            }
            Switch(
                modifier = Modifier.height(64.dp),
                checked = isEditMode.value,
                onCheckedChange = { isEditMode.value = it })

            LazyVerticalGrid(
                modifier = Modifier.fillMaxSize(),
                cells = GridCells.Fixed(3)
            ) {
                items(playerState.players) { player ->
                    PlayerItem(
                        player = player,
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable {
                                if (isEditMode.value) {
                                    playerViewModel.onEvent(PlayerEvent.DeletePlayer(player))
                                } else {
                                    playViewModel.onEvent(PlayEvent.LogPlay(player.name))
                                }
                            }
                    )
                }
            }

        }


    }

}



