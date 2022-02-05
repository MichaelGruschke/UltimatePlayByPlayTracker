package com.example.ultimateplaybyplaytracker.feature_tracker.presentation.tracker

import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Save
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.ultimateplaybyplaytracker.feature_tracker.presentation.tracker.components.TransparentHintTextField
import kotlinx.coroutines.flow.collectLatest

@Composable
fun AddPlayerScreen(
    navController: NavController,
    viewModel: AddPlayerViewModel = hiltViewModel()
) {
    val nameState = viewModel.playerName.value
    val scaffoldState = rememberScaffoldState()

    LaunchedEffect(key1 = true) {
        viewModel.eventFlow.collectLatest { event ->
            when (event) {
                is AddPlayerViewModel.UiEvent.ShowSnackbar -> {
                    scaffoldState.snackbarHostState.showSnackbar(
                        message = event.message
                    )
                }
                is AddPlayerViewModel.UiEvent.SaveNote -> {
                    navController.navigateUp()
                }
            }
        }
    }

    Scaffold(
        floatingActionButton = {
            FloatingActionButton(onClick = {
                viewModel.onEvent(AddPlayerEvent.SavePlayer)
            },
             backgroundColor = MaterialTheme.colors.primary
            ) {
                Icon(imageVector = Icons.Default.Save, contentDescription = "Save player")
            }
        },
        scaffoldState = scaffoldState
    ) {
        Column(modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)) {
            Spacer(modifier = Modifier.height(16.dp))
            TransparentHintTextField(
                text = nameState.text,
                hint = nameState.hint,
                onValueChange = {
                                viewModel.onEvent(AddPlayerEvent.EntreredTitle(it))
                },
                onFocusChange = {
                    viewModel.onEvent(AddPlayerEvent.ChangeTitleFocus(it))
                },
                isHintVisisble = nameState.isHintVisisble,
                singleLine = true,
                textStyle = MaterialTheme.typography.h3
            )
        }
    }

}