package com.example.calculator.ui.screens.calculus.bezierCurve

import android.content.res.Configuration
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.ZoomIn
import androidx.compose.material.icons.filled.ZoomOut
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.graphics.nativeCanvas
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.calculator.navigation.AppRoute
import com.example.calculator.ui.components.FunctionField
import com.example.calculator.ui.components.SideMenu
import com.example.calculator.ui.theme.AppTheme
import com.example.calculator.ui.utils.HSpacer
import com.example.calculator.ui.utils.VSpacer
import com.example.calculator.utlis.bezierCurve
import com.example.calculator.utlis.scalePoint
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BezierCurvesScreen(
    viewModel: BezierCurvesViewModel,
    onNavigate: (AppRoute) -> Unit
) {
    val state by viewModel.uiState.collectAsStateWithLifecycle()
    val scope = rememberCoroutineScope()
    val drawerState =
        rememberDrawerState(initialValue = androidx.compose.material3.DrawerValue.Closed)

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
                    title = { Text(text = "Bezier Curves") },
                    actions = {

                    }
                )
            },
            modifier = Modifier.fillMaxWidth()
        ) { paddingValues ->

            BezierCurvesContent(
                state = state,
                paddingValues = paddingValues,
                onResetView = {

                },
                onPan = { x, y ->

                },
                onZoomIn = { scale ->

                },
                onZoomOut = { scale ->

                },
                onColorClick = {

                },
                onClearClick = {

                },
                onTextFieldEdit = { index, value ->
                    viewModel.setEvent(BezierCurvesContract.Event.TextFieldEdit(index, value))
                }
            )
        }
    }
}

@Composable
fun BezierCurvesContent(
    state: BezierCurvesContract.State,
    paddingValues: PaddingValues,
    onResetView: () -> Unit,
    onPan: (Float, Float) -> Unit,
    onZoomIn: (Float) -> Unit,
    onZoomOut: (Float) -> Unit,
    onTextFieldEdit: (Int, String) -> Unit,
    onColorClick: (Int) -> Unit,
    onClearClick: (Int) -> Unit
) {
    var isDragging by remember { mutableStateOf(false) }
    val step = 50f
    val textPaint = remember {
        android.graphics.Paint().apply {
            color = android.graphics.Color.DKGRAY
            textSize = 24f
            textAlign = android.graphics.Paint.Align.CENTER
            isAntiAlias = true
        }
    }
    // Canvas that shows labeled the start, end and control points and the corresponding Bezier curve
    // The points are shown as red dots and the curve is shown as a blue line
    // The points can be dragged to change the curve
    val curve = bezierCurve(
        start = state.start,
        end = state.end,
        controlPoints = state.controlPoints
    )
    Column(modifier = Modifier.padding(paddingValues)) {
        Box(
            modifier = Modifier
                .weight(3f)
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
                        onDragStart = { isDragging = true },
                        onDragEnd = { isDragging = false },
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
                        scaleX = state.scale
                        scaleY = state.scale
                        translationX = state.offsetX
                        translationY = state.offsetY
                    }
            ) {
                val nativeCanvas = drawContext.canvas.nativeCanvas
                val originX = size.width / 2f + state.offsetX * state.scale
                val originY = size.height / 2f + state.offsetY * state.scale

                // Calculate visible range in graph coordinates
                val scale = state.scale
                var minX = (-originX - state.offsetX) / (step * scale)
                var maxX = (size.width - originX - state.offsetX) / (step * scale)
                var minY = (originY + state.offsetY - size.height) / (step * scale)
                var maxY = (originY + state.offsetY) / (step * scale)

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
                // Function to convert Cartesian coordinates to Canvas Offset using the graph's scale
                fun graphToCanvas(x: Float, y: Float): Offset {
                    // X = OriginX + (Cartesian X * Step * Scale)
                    val canvasX = originX + x * step * scale
                    // Y = OriginY - (Cartesian Y * Step * Scale) (Y is inverted for canvas)
                    val canvasY = originY - y * step * scale
                    return Offset(canvasX, canvasY)
                }

                // Draw the curve
                if (curve.size >= 2) {
                    // Use scale.coerceAtLeast(1f) to prevent lines from becoming too thin/thick
                    val curveStroke = 6f / scale.coerceAtLeast(1f)
                    var prevPoint = graphToCanvas(curve[0].x, curve[0].y)

                    for (i in 1 until curve.size) {
                        val currentPoint = graphToCanvas(curve[i].x, curve[i].y)
                        drawLine(
                            color = Color.Blue,
                            start = prevPoint,
                            end = currentPoint,
                            strokeWidth = curveStroke
                        )
                        prevPoint = currentPoint
                    }
                }

                // Draw control points
                state.controlPoints.forEach { point ->
                    val scaledPoint = graphToCanvas(point.first, point.second)
                    drawCircle(
                        color = state.colors[state.controlPoints.indexOf(point) + 2],
                        center = scaledPoint,
                        radius = 8f / scale.coerceAtLeast(1f)
                    )
                }

                // Draw start and end points
                listOf(state.start, state.end).forEach { point ->
                    val scaledPoint = graphToCanvas(point.first, point.second)
                    drawCircle(
                        color = if (point == state.start) state.colors[0] else state.colors[1],
                        center = scaledPoint,
                        radius = 10f / scale.coerceAtLeast(1f)
                    )
                }
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
        Row(modifier = Modifier.weight(2f)){
            Column(){
                FunctionField(
                    label = "Start Point",
                    value = state.textFieldValues[0],
                    onValueChange = {
                        onTextFieldEdit(0, it)
                    },
                    color = state.colors[0]
                )
                FunctionField(
                    label = "End Point",
                    value = state.textFieldValues[1],
                    onValueChange = {
                        onTextFieldEdit(1, it)
                    },
                    color = state.colors[1]
                )
                state.controlPoints.forEachIndexed { index, point ->
                    FunctionField(
                        label = "Control Point ${index + 1}",
                        value = state.textFieldValues[index + 2],
                        onValueChange = {
                            onTextFieldEdit(index + 2, it)
                        },
                        color = state.colors[index + 2]
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun BezierCurvesContentPreview() {
    AppTheme {
        BezierCurvesScreen(
            viewModel = BezierCurvesViewModel(),
            onNavigate = {}
        )
    }
}
