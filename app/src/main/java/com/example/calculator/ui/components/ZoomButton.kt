package com.example.calculator.ui.components

import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Remove
import androidx.compose.material.icons.filled.ZoomIn
import androidx.compose.material.icons.filled.ZoomOut
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

enum class Zoom {
    IN,
    OUT
}

val zoomIcons = mapOf(
    Zoom.IN to Icons.Filled.Add,
    Zoom.OUT to Icons.Filled.Remove
)

@Composable
fun ZoomButton(
    zoom: Zoom,
    onZoomChange: (Zoom) -> Unit
) {
    IconButton(
        onClick = {
            onZoomChange(zoom)
        }
    ) {
        Icon(
            imageVector = zoomIcons[zoom]!!,
            contentDescription = "Zoom"
        )
    }
}
