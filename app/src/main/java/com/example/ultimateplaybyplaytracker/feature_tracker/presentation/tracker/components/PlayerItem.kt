package com.example.ultimateplaybyplaytracker.feature_tracker.presentation.tracker.player.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.example.ultimateplaybyplaytracker.feature_tracker.domain.model.Player

@Composable
fun PlayerItem(
    player: Player,
    modifier: Modifier,
    isPlaying: Boolean,
) {
    Box(
        modifier = modifier
            .padding(8.dp)
            .background(color = if (player.isMeta) MaterialTheme.colors.surface else if (isPlaying) Color.White else MaterialTheme.colors.onSurface)
            .border(width = 1.dp, color = Color.White)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = player.name,
                style = MaterialTheme.typography.h5,
                color = if (isPlaying || player.isMeta) MaterialTheme.colors.onSurface else Color.White,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
            )
        }
    }
}