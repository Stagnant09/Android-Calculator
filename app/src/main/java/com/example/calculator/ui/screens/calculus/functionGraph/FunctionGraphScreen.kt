package com.example.calculator.ui.screens.calculus.functionGraph

import android.annotation.SuppressLint
import android.content.res.Configuration
import android.util.Log
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.nativeCanvas
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.calculator.navigation.AppRoute
import com.example.calculator.ui.components.FunctionField
import com.example.calculator.ui.components.SideMenu
import com.example.calculator.ui.components.Zoom
import com.example.calculator.ui.components.ZoomButton
import com.example.calculator.ui.theme.AppTheme
import com.example.calculator.ui.utils.VSpacer
import kotlinx.coroutines.launch
import kotlin.math.roundToInt

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FunctionGraphScreen(
    viewModel: FunctionGraphViewModel,
    onNavigate: (AppRoute) -> Unit
) {
    val state by viewModel.uiState.collectAsStateWithLifecycle()
    val scope = rememberCoroutineScope()
    val drawerState = rememberDrawerState(DrawerValue.Closed)

    SideMenu(
        onNavigate = onNavigate,
        drawerState = drawerState
    ) {
        Scaffold(
            topBar = {
                TopAppBar(
                    navigationIcon = {
                        IconButton(onClick = {
                            scope.launch { drawerState.open() }
                        }) {
                            Icon(
                                painter = rememberVectorPainter(Icons.Default.Menu),
                                contentDescription = "Menu"
                            )
                        }
                    },
                    title = { Text(text = "Function Graph") },
                )
            },
            modifier = Modifier.fillMaxWidth()
        ) { paddingValues ->
            FunctionGraphScreenContent(
                modifier = Modifier.padding(paddingValues),
                state = state,
                onTextFieldEdit = { index, input ->
                    viewModel.setEvent(
                        FunctionGraphContract.Event.UpdateTextField(
                            index,
                            input
                        )
                    )
                }
            )
        }
    }
}

@Composable
fun FunctionGraphScreenContent(
    modifier: Modifier,
    state: FunctionGraphContract.State,
    onTextFieldEdit: (Int, String) -> Unit
) {
    val step = 50f
    val textPaint = remember {
        android.graphics.Paint().apply {
            color = android.graphics.Color.DKGRAY
            textSize = 24f
            textAlign = android.graphics.Paint.Align.CENTER
            isAntiAlias = true
        }
    }
    Column {
        Box(
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth()
        ) {
            Canvas(modifier = Modifier.fillMaxSize()) {
                val nativeCanvas = drawContext.canvas.nativeCanvas
                val originX = size.width / 2f
                val originY = size.height / 2f

                // --- Draw grid lines ---
                for (x in 0..(size.width / step).toInt()) {
                    val xPos = (x * step).roundToInt().toFloat()
                    drawLine(Color.DarkGray, Offset(xPos, 0f), Offset(xPos, size.height))
                }
                for (y in 0..(size.height / step).toInt()) {
                    val yPos = (y * step).roundToInt().toFloat()
                    drawLine(Color.DarkGray, Offset(0f, yPos), Offset(size.width, yPos))
                }

                // --- Draw axes ---
                drawLine(Color.Black, Offset(originX, 0f), Offset(originX, size.height)) // Y-axis
                drawLine(Color.Black, Offset(0f, originY), Offset(size.width, originY))   // X-axis

                // --- Draw axis labels ---
                val numStepsX = (size.width / step / 2).toInt()
                val numStepsY = (size.height / step / 2).toInt()

                // X-axis labels
                for (i in -numStepsX..numStepsX) {
                    val xPos = originX + i * step
                    val label = i.toString()
                    if (i != 0) {
                        nativeCanvas.drawText(label, xPos, originY + 24f, textPaint)
                    }
                }

                // Y-axis labels
                for (i in -numStepsY..numStepsY) {
                    val yPos = originY - i * step
                    if (i != 0) { // skip the origin label (we'll draw O separately)
                        nativeCanvas.drawText(i.toString(), originX + 24f, yPos + 8f, textPaint)
                    }
                }

                // --- Origin label ---
                nativeCanvas.drawText("O", originX - 16f, originY + 24f, textPaint)

                // Draw functions
                state.functions.forEachIndexed { index, expression ->
                    val pathColor = state.functionColors.getOrNull(index) ?: Color.Blue
                    val pathPoints = mutableListOf<Offset>()

                    for (pixelX in 0..size.width.toInt()) {
                        val xCartesian = (pixelX - originX) / step
                        val yCartesian = try {
                            -expression.evaluate(mapOf("x" to xCartesian.toDouble(), "y" to 0.0)).toFloat()
                        } catch (e: Exception) {
                            continue
                        }
                        if (!yCartesian.isFinite()) continue

                        val canvasX = xToCanvas(xCartesian, originX, step)
                        val canvasY = yToCanvas(yCartesian, originY, step)
                        pathPoints.add(Offset(canvasX, canvasY))
                    }

                    for (i in 0 until pathPoints.size - 1) {
                        drawLine(
                            color = pathColor,
                            start = pathPoints[i],
                            end = pathPoints[i + 1],
                            strokeWidth = 2f
                        )
                    }
                }
            }
            Column(modifier = Modifier.padding(horizontal = 16.dp)) {
                VSpacer(360)
                for (i in Zoom.entries) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.End
                    ) {
                        ZoomButton(zoom = i, onZoomChange = { })
                    }
                    VSpacer(4)
                }
            }

        }

        Row(
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth()
        ) {
            Column() {
                FunctionField(
                    color = Color.Blue,
                    value = state.textFieldsContent[0],
                    onValueChange = { onTextFieldEdit(0, it) },
                    onColorClick = { /* open color picker later */ }
                )
                FunctionField(
                    color = Color.Magenta,
                    value = state.textFieldsContent[1],
                    onValueChange = { onTextFieldEdit(1, it) },
                    onColorClick = { /* open color picker later */ }
                )
            }
        }
    }

}

@SuppressLint("ViewModelConstructorInComposable")
@Preview(showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun FunctionGraphScreenPreview() {
    val viewModel = FunctionGraphViewModel()
    AppTheme {
        FunctionGraphScreen(
            viewModel = viewModel,
            onNavigate = {}
        )
    }
}

fun xToCanvas(x: Float, originX: Float, step: Float): Float = originX + x * step
fun yToCanvas(y: Float, originY: Float, step: Float): Float = originY - y * step
