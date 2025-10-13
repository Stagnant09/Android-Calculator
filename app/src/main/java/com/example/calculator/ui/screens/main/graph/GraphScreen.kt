package com.example.calculator.ui.screens.main.graph

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Paint
import androidx.compose.ui.graphics.nativeCanvas
import androidx.compose.ui.input.pointer.pointerInput
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.calculator.navigation.AppRoute
import com.example.calculator.ui.components.SideMenu

@Composable
fun GraphScreen(
    viewModel: GraphViewModel = viewModel(),
    onNavigate: (AppRoute) -> Unit
) {
    val state by viewModel.uiState.collectAsState()

    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val scope = rememberCoroutineScope()

    SideMenu(
        onNavigate = onNavigate,
        drawerState = drawerState
    ) {

        Box(modifier = Modifier.fillMaxSize()) {
            Canvas(
                modifier = Modifier
                    .fillMaxSize()
                    .pointerInput(Unit) {
                        detectTapGestures(
                            onTap = { offset ->
                                viewModel.setEvent(
                                    GraphScreenContract.Event.AddNode(
                                        offset
                                    )
                                )
                            }
                        )
                    }
            ) {
                // Draw edges
                state.edges.forEach { edge ->
                    val from = state.nodes.find { it.id == edge.from } ?: return@forEach
                    val to = state.nodes.find { it.id == edge.to } ?: return@forEach

                    drawLine(
                        color = if (edge.from in state.shortestPath && edge.to in state.shortestPath)
                            Color.Green else Color.Gray,
                        start = from.position,
                        end = to.position,
                        strokeWidth = 4f
                    )

                    // Draw weight label
                    val mid = Offset(
                        (from.position.x + to.position.x) / 2,
                        (from.position.y + to.position.y) / 2
                    )
                    drawContext.canvas.nativeCanvas.drawText(
                        edge.weight.toString(),
                        mid.x,
                        mid.y,
                        Paint().asFrameworkPaint().apply {
                            color = android.graphics.Color.BLACK
                            textSize = 36f
                            textAlign = android.graphics.Paint.Align.CENTER
                        }
                    )
                }

                // Draw nodes
                state.nodes.forEach { node ->
                    drawCircle(
                        color = if (node.id in state.shortestPath) Color.Green else Color.Blue,
                        radius = 30f,
                        center = node.position
                    )
                    drawContext.canvas.nativeCanvas.drawText(
                        node.id.toString(),
                        node.position.x,
                        node.position.y - 40f,
                        Paint().asFrameworkPaint().apply {
                            color = android.graphics.Color.BLACK
                            textSize = 32f
                            textAlign = android.graphics.Paint.Align.CENTER
                        }
                    )
                }
            }
        }
    }
}