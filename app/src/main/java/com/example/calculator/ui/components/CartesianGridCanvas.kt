package com.example.calculator.ui.components

import android.R.attr.textSize
import android.text.TextPaint
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ZoomIn
import androidx.compose.material.icons.filled.ZoomOut
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Canvas
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.graphics.nativeCanvas
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.calculator.foundation.CustomState

/** This composable is a canvas that draws a Cartesian grid
 * with labels and zoom-in/zoom-out buttons
 */
@Composable
fun CartesianGridCanvas(
    scale: Float,
    offsetX: Float,
    offsetY: Float,
    step: Float,
    onPan: (Float, Float) -> Unit,
    onZoomIn: (Float) -> Unit,
    onZoomOut: (Float) -> Unit,
    onResetView: () -> Unit,
    onDragStart: (Offset) -> Unit,
    onDragEnd: () -> Unit,
    onDrag: (Float, Float) -> Unit,
    drawExtra: () -> Unit = {}
) {
    val textPaint = remember {
        android.graphics.Paint().apply {
            color = android.graphics.Color.DKGRAY
            textSize = 24f
            textAlign = android.graphics.Paint.Align.CENTER
            isAntiAlias = true
        }
    }
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .pointerInput(Unit) {
                detectTapGestures(
                    onDoubleTap = {
                        onResetView()
                    }
                )
            }
            .pointerInput(Unit) {
                detectDragGestures(
                    onDragStart = onDragStart,
                    onDragEnd = onDragEnd,
                    onDrag = { change, dragAmount ->
                        change.consume()
                        onPan(dragAmount.x, dragAmount.y)
                    }
                )
            }
    ) {
        Canvas(
            modifier = Modifier
                .fillMaxSize()
                .graphicsLayer {
                    scaleX = scale
                    scaleY = scale
                    translationX = offsetX
                    translationY = offsetY
                }
        ) {
            val nativeCanvas = drawContext.canvas.nativeCanvas
            val originX = size.width / 2f + offsetX * scale
            val originY = size.height / 2f + offsetY * scale

            // Calculate visible range in graph coordinates
            val scale = scale
            val minX = (-originX - offsetX) / (step * scale)
            val maxX = (size.width - originX - offsetX) / (step * scale)
            val minY = (originY + offsetY - size.height) / (step * scale)
            val maxY = (originY + offsetY) / (step * scale)

            // Calculate grid line step based on zoom level
            val gridStep = when {
                scale > 5f -> 0.5f
                scale > 2f -> 1f
                scale > 0.5f -> 2f
                else -> 5f
            }

            // Draw vertical grid lines
            val startX = (minX / gridStep).toInt() * gridStep - gridStep
            val endX = maxX + gridStep * 2
            var x1 = startX
            while (x1 <= endX) {
                val xPos = originX + x1 * step * scale
                if (xPos in -100f..(size.width + 100)) {
                    drawLine(
                        color = Color.DarkGray.copy(
                            alpha = if (x1.toInt().toFloat() == x1) 0.5f else 0.2f
                        ),
                        start = Offset(xPos, 0f),
                        end = Offset(xPos, size.height),
                        strokeWidth = if (x1.toInt().toFloat() == x1) 1f else 0.5f
                    )
                }
                x1 += gridStep
            }

            // Draw horizontal grid lines
            val startY = (minY / gridStep).toInt() * gridStep - gridStep
            val endY = maxY + gridStep * 2
            var y1 = startY
            while (y1 <= endY) {
                val yPos = originY - y1 * step * scale
                if (yPos in -100f..(size.height + 100)) {
                    drawLine(
                        color = Color.DarkGray.copy(
                            alpha = if (y1.toInt().toFloat() == y1) 0.5f else 0.2f
                        ),
                        start = Offset(0f, yPos),
                        end = Offset(size.width, yPos),
                        strokeWidth = if (y1.toInt().toFloat() == y1) 1f else 0.5f
                    )
                }
                y1 += gridStep
            }

            // --- Draw axes ---
            // X-axis
            drawLine(
                Color.Black,
                Offset(0f, originY),
                Offset(size.width, originY),
                strokeWidth = 2f * scale.coerceIn(0.5f, 2f)
            )

            // Y-axis
            drawLine(
                Color.Black,
                Offset(originX, 0f),
                Offset(originX, size.height),
                strokeWidth = 2f * scale.coerceIn(0.5f, 2f)
            )

            // --- Draw axis labels ---
            // X-axis labels
            var x = startX
            while (x <= endX) {
                if (x.toInt().toFloat() == x && x != 0f) {
                    val xPos = originX + x * step * scale
                    if (xPos in -50f..(size.width + 50)) {
                        val label = x.toInt().toString()
                        nativeCanvas.drawText(
                            label,
                            xPos,
                            (originY + 24f * scale).coerceIn(0f, size.height),
                            textPaint.apply {
                                textSize = 26f * scale.coerceIn(0.5f, 2f)
                            }
                        )
                    }
                }
                x += gridStep
            }

            // Y-axis labels
            var y = startY
            while (y <= endY) {
                if (y.toInt().toFloat() == y && y != 0f) {
                    val yPos = originY - y * step * scale
                    if (yPos in -50f..(size.height + 50)) {
                        val label = y.toInt().toString()
                        nativeCanvas.drawText(
                            label,
                            (originX + 24f * scale).coerceIn(0f, size.width),
                            yPos + 8f * scale,
                            textPaint.apply {
                                textSize = 26f * scale.coerceIn(0.5f, 2f)
                            }
                        )
                    }
                }
                y += gridStep
            }

            // --- Draw axes ---
            // X-axis
            drawLine(
                Color.Black,
                Offset(0f, originY),
                Offset(size.width, originY),
                strokeWidth = 2f * scale.coerceIn(0.5f, 2f)
            )

            // Y-axis
            drawLine(
                Color.Black,
                Offset(originX, 0f),
                Offset(originX, size.height),
                strokeWidth = 2f * scale.coerceIn(0.5f, 2f)
            )

            // Origin label
            if (originX in -50f..(size.width + 50) && originY in -50f..(size.height + 50)) {
                nativeCanvas.drawText(
                    "O",
                    originX - 16f * scale,
                    originY + 24f * scale,
                    textPaint.apply {
                        textSize = 16f * scale.coerceIn(0.5f, 2f)
                    }
                )
            }

            drawExtra()
        }
        // Zoom controls
        Column(
            modifier = Modifier
                .padding(16.dp)
                .clip(CircleShape)
                .background(MaterialTheme.colorScheme.surface.copy(alpha = 0.7f))
                .padding(8.dp)
        ) {
            // Zoom in button
            IconButton(
                onClick = {
                    onZoomIn(2f)
                },
                modifier = Modifier.size(48.dp)
            ) {
                Icon(Icons.Default.ZoomIn, contentDescription = "Zoom In")
            }

            // Zoom out button
            IconButton(
                onClick = {
                    onZoomOut(0.5f)
                },
                modifier = Modifier.size(48.dp)
            ) {
                Icon(Icons.Default.ZoomOut, contentDescription = "Zoom Out")
            }

            // Reset view button
            TextButton(
                onClick = {
                    onResetView()
                },
                modifier = Modifier.size(48.dp)
            ) {
                Text("1:1", fontSize = 12.sp, textAlign = TextAlign.Center)
            }
        }
    }
}
