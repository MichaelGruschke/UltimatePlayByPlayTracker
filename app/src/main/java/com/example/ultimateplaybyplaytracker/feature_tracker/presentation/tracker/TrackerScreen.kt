package com.example.ultimateplaybyplaytracker.feature_tracker.presentation.tracker


import android.Manifest
import android.os.Build
import androidx.annotation.RequiresApi

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.GridCells
import androidx.compose.foundation.lazy.LazyVerticalGrid
import androidx.compose.foundation.lazy.items

import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.text.style.TextOverflow
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.ultimateplaybyplaytracker.feature_tracker.presentation.tracker.player.components.PlayerItem
import com.example.ultimateplaybyplaytracker.feature_tracker.presentation.utils.Screen
import androidx.compose.ui.unit.dp
import com.example.ultimateplaybyplaytracker.feature_tracker.presentation.tracker.Play.PlayEvent
import com.example.ultimateplaybyplaytracker.feature_tracker.presentation.tracker.Play.PlayViewModel
import com.example.ultimateplaybyplaytracker.feature_tracker.presentation.tracker.player.PlayerEvent
import com.example.ultimateplaybyplaytracker.feature_tracker.presentation.tracker.player.PlayerViewModel
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.rememberMultiplePermissionsState
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch


@ExperimentalFoundationApi
@ExperimentalPermissionsApi
@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun TrackerScreen(
    navController: NavController,
    playerViewModel: PlayerViewModel = hiltViewModel(),
    playViewModel: PlayViewModel = hiltViewModel()
) {
    val playerState = playerViewModel.state.value
    val playState = playViewModel.state.value
    val scaffoldState = rememberScaffoldState()
    val scope = rememberCoroutineScope()

    val screenWidth = LocalConfiguration.current.screenWidthDp
    val txtWidth = screenWidth * 0.85
    val backWidth = screenWidth * 0.15

    val isEditPlayerMode = remember { mutableStateOf(false) }
    val isO = remember { mutableStateOf(true) }
    val isSelectLineMode  = remember { mutableStateOf(false) }

    val permissionState = rememberMultiplePermissionsState(
        permissions = listOf(
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
        )
    )


    LaunchedEffect(key1 = true) {
        playViewModel.eventFlow.collectLatest { event ->
            when (event) {
                is PlayViewModel.TrackerUiEvent.ShowSnackbar -> {
                    scaffoldState.snackbarHostState.showSnackbar(
                        message = event.message
                    )
                }
            }
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Ultimate Tracker") },
                actions = {
                    // RowScope here, so these icons will be placed horizontally
                    if (isEditPlayerMode.value) {
                        IconButton(onClick = {
                            isEditPlayerMode.value = false
                            scope.launch {
                                scaffoldState.snackbarHostState.showSnackbar(
                                    message = "Enter Recording Mode",
                                    duration = SnackbarDuration.Short
                                )
                            }
                        }) {
                            Icon(Icons.Filled.Done, contentDescription = "Leave Edit Mode")
                        }
                    } else {
                        IconButton(onClick = {
                            isEditPlayerMode.value = true
                            scope.launch {
                                scaffoldState.snackbarHostState.showSnackbar(
                                    message = "Enter Edit Mode",
                                    duration = SnackbarDuration.Short
                                )
                            }
                        }) {
                            Icon(Icons.Filled.Edit, contentDescription = "Enter Edit Mode")
                        }
                    }
                    IconButton(onClick = {
                        permissionState.launchMultiplePermissionRequest()
                        playViewModel.onEvent(PlayEvent.ExportPlays)
                    }) {
                        Icon(Icons.Filled.Download, contentDescription = "Export JSON")
                    }
                    IconButton(onClick = {
                        scope.launch {
                            val result = scaffoldState.snackbarHostState.showSnackbar(
                                message = "Delete Log?",
                                actionLabel = "Confirm",
                                duration = SnackbarDuration.Short
                            )
                            when (result) {
                                SnackbarResult.ActionPerformed -> {
                                    playViewModel.onEvent(PlayEvent.DeletaAll)
                                }
                            }
                        }


                    }) {
                        Icon(Icons.Filled.Delete, contentDescription = "Delete Play Table")
                    }
                }
            )
        },
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
        floatingActionButtonPosition = FabPosition.End,
        isFloatingActionButtonDocked = true,
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
                horizontalArrangement = Arrangement.End,
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Text(
                    modifier = Modifier.width(txtWidth.dp),
                    text = playState.plays.takeLast(5).map { item ->
                        item.event
                    }.joinToString(" > "),
                    color = Color.Black,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis
                )

                Button(
                    modifier = Modifier.requiredWidth(backWidth.dp),
                    onClick = {
                        playViewModel.onEvent(PlayEvent.RevertPlay(playState.plays.last()))
                    }, colors = ButtonDefaults.buttonColors(
                        backgroundColor = Color.Gray,
                        contentColor = MaterialTheme.colors.surface
                    ), enabled = playState.plays.isNotEmpty()
                ) {
                    Icon(imageVector = Icons.Default.Backspace, "")
                }
            }
            Row(
                modifier = Modifier.fillMaxWidth().padding(5.dp),
                horizontalArrangement = Arrangement.Start,
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Button(
                    modifier=Modifier.width(128.dp),
                    colors = ButtonDefaults.buttonColors(
                        backgroundColor = Color.Gray,
                        contentColor = MaterialTheme.colors.surface
                    ),
                    onClick = { isSelectLineMode.value = !isSelectLineMode.value }) {
                    Text(text = if (isSelectLineMode.value) "Save Line" else "Select Line")
                }
                Spacer(modifier = Modifier.width(16.dp))
                RadioButton(
                    colors=RadioButtonDefaults.colors(unselectedColor = Color.White),
                    selected = isO.value,
                    onClick = {
                        isO.value = true
                    })
                Text(text = "O-Line", modifier=Modifier.padding(5.dp))
                RadioButton(
                    colors=RadioButtonDefaults.colors(unselectedColor = Color.White),
                    selected = !isO.value,
                    onClick = {
                        isO.value = false
                    })
                Text(text = "D-Line", modifier=Modifier.padding(5.dp))
            }
            Divider(color = Color.DarkGray, thickness = 2.dp)
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
                                if (isEditPlayerMode.value) {
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
