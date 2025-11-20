package com.example.calculator.ui.screens.calculus.functionGraph

import android.annotation.SuppressLint
import android.content.res.Configuration
import android.util.Log
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.ZoomIn
import androidx.compose.material.icons.filled.ZoomOut
import androidx.compose.material3.Checkbox
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.graphics.nativeCanvas
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.calculator.navigation.AppRoute
import com.example.calculator.ui.components.CircularColorPicker
import com.example.calculator.ui.components.FunctionField
import com.example.calculator.ui.components.SideMenu
import com.example.calculator.ui.theme.AppTheme
import com.example.calculator.ui.utils.VSpacer
import com.example.calculator.utlis.Expression
import com.example.calculator.utlis.ExpressionForm
import com.example.calculator.utlis.ImplicitEvaluator
import com.example.calculator.utlis.containsDependent
import kotlinx.coroutines.launch
import kotlin.math.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FunctionGraphScreen(
    viewModel: FunctionGraphViewModel,
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
                    title = { Text(text = "Function Graph") },
                    actions = {
                        IconButton(onClick = {
                            viewModel.setEvent(FunctionGraphContract.Event.TappedSettingsButton)
                        }) {
                            Icon(
                                painter = rememberVectorPainter(Icons.Default.Settings),
                                contentDescription = "Settings"
                            )
                        }
                    }
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
                },
                onFunctionAddTapped = {
                    viewModel.setEvent(FunctionGraphContract.Event.AddFunction)
                },
                onFunctionRemoveTapped = {
                    viewModel.setEvent(FunctionGraphContract.Event.RemoveFunction(it))
                },
                onSetCurrentIndex = {
                    viewModel.setEvent(FunctionGraphContract.Event.SetCurrentIndex(it))
                },
                onToggledColorPicker = {
                    viewModel.setEvent(FunctionGraphContract.Event.ToggledColorPicker)
                },
                onZoomIn = { factor ->
                    viewModel.setEvent(FunctionGraphContract.Event.ZoomIn(factor))
                },
                onZoomOut = { factor ->
                    viewModel.setEvent(FunctionGraphContract.Event.ZoomOut(factor))
                },
                onPan = { dx, dy ->
                    viewModel.setEvent(FunctionGraphContract.Event.Pan(dx, dy))
                },
                onResetView = {
                    viewModel.setEvent(FunctionGraphContract.Event.ResetView)
                }
            )
        }
    }

    if (state.showBottomSheet) {
        ModalBottomSheet(
            onDismissRequest = {
                viewModel.setEvent(FunctionGraphContract.Event.DismissBottomSheet)
            }
        ) {
            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(0.9f),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(text = "Show intersection points")
                    Checkbox(
                        checked = state.showIntersectionPoints,
                        onCheckedChange = {
                            viewModel.setEvent(FunctionGraphContract.Event.ToggledIntersectionPoints)
                        }
                    )
                }
                Row(
                    modifier = Modifier.fillMaxWidth(0.9f),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(text = "Show labels")
                    Checkbox(
                        checked = state.showLabels,
                        onCheckedChange = {
                            viewModel.setEvent(FunctionGraphContract.Event.ToggledLabels)
                        }
                    )
                }
            }
        }
    }

    if (state.showColorPicker) {
        CircularColorPicker(
            onDismissRequest = {
                viewModel.setEvent(FunctionGraphContract.Event.ToggledColorPicker)
            },
            onConfirm = { color ->
                viewModel.setEvent(
                    FunctionGraphContract.Event.SetFunctionColor(
                        state.currentIndex,
                        color
                    )
                )
                viewModel.setEvent(FunctionGraphContract.Event.ToggledColorPicker)
            }
        )
    }
}

@Composable
fun FunctionGraphScreenContent(
    modifier: Modifier,
    state: FunctionGraphContract.State,
    onTextFieldEdit: (Int, String) -> Unit,
    onFunctionAddTapped: () -> Unit,
    onFunctionRemoveTapped: (Int) -> Unit,
    onSetCurrentIndex: (Int) -> Unit,
    onToggledColorPicker: () -> Unit,
    onZoomIn: (Float) -> Unit = {},
    onZoomOut: (Float) -> Unit = {},
    onPan: (Float, Float) -> Unit = { _, _ -> },
    onResetView: () -> Unit = {}
) {
    val scope = rememberCoroutineScope()
    var isDragging by remember { mutableStateOf(false) }
    val step = 50f
    val angleStep = 10f
    val textPaint = remember {
        android.graphics.Paint().apply {
            color = android.graphics.Color.DKGRAY
            textSize = 24f
            textAlign = android.graphics.Paint.Align.CENTER
            isAntiAlias = true
        }
    }

    Column(modifier = modifier) {
        Box(
            modifier = Modifier
                .weight(1f)
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
                val minX = (-originX - state.offsetX) / (step * scale)
                val maxX = (size.width - originX - state.offsetX) / (step * scale)
                val minY = (originY + state.offsetY - size.height) / (step * scale)
                val maxY = (originY + state.offsetY) / (step * scale)

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

                // Draw functions
                state.functions.forEachIndexed { index, expression ->
                    val pathColor = state.functionColors.getOrNull(index) ?: Color.Blue
                    val pathPoints = mutableListOf<Offset>()
                    val strokeWidth = 3f * scale.coerceIn(0.5f, 2f)
                    Log.d("FunctionGraphScreen", "expression: ${expression.isVerticalLine}")
                    if (expression.isVerticalLine.first) {
                        // Draw vertical line
                        Log.d(
                            "FunctionGraphScreen",
                            "Vertical line: ${expression.isVerticalLine.second}"
                        )
                        val x = expression.isVerticalLine.second
                        val xPos = size.width / 2f + x * step * scale + state.offsetX * state.scale
                        drawLine(
                            pathColor,
                            Offset(xPos, 0f),
                            Offset(xPos, size.height),
                            strokeWidth = strokeWidth
                        )
                        // Skip the rest of the block and proceed to the next function
                        return@forEachIndexed
                    }
                    if (expression.isImplicit) {
                        val evaluator = ImplicitEvaluator(expression)

                        val mask = NativePlot.computeImplicit(
                            width = size.width.toInt(),
                            height = size.height.toInt(),
                            originX = originX,
                            originY = originY,
                            step = step,
                            scale = scale,
                            threshold = 0.02f / scale,
                            evaluator = evaluator
                        )

                        mask.forEachIndexed { i, value ->
                            if (value == 1) {
                                val x = i % size.width.toInt()
                                val y = i / size.width.toInt()
                                drawCircle(
                                    color = pathColor,
                                    center = Offset(x.toFloat(), y.toFloat()),
                                    radius = strokeWidth
                                )
                            }
                        }

                        return@forEachIndexed
                    }
                    if (expression.form == ExpressionForm.CARTESIAN) {
                        // Fallback check for "x = c, where c a constant"

                        // Calculate the range of x values to evaluate
                        // Add some padding to ensure smooth edges when panning
                        val padding = 2f / scale
                        val startX = minX - padding
                        val endX = maxX + 1f
                        val stepX =
                            1f / (scale * 2).coerceAtMost(1f) // More points when zoomed in

                        // Evaluate the function at multiple points
                        var x = startX
                        while (x <= endX) {
                            try {
                                val yCartesian = -expression.evaluate(
                                    mapOf(
                                        "x" to x.toDouble(),
                                        "y" to 0.0
                                    )
                                ).toFloat()

                                if (yCartesian.isFinite()) {
                                    val canvasX = originX + x * step * scale
                                    val canvasY = originY - yCartesian * step * scale
                                    pathPoints.add(Offset(canvasX, canvasY))
                                }
                            } catch (e: Exception) {
                                // Skip points that can't be evaluated
                                if (pathPoints.isNotEmpty()) {
                                    // Draw the current segment before the discontinuity
                                    if (pathPoints.size > 1) {
                                        for (i in 0 until pathPoints.size - 1) {
                                            drawLine(
                                                color = pathColor,
                                                start = pathPoints[i],
                                                end = pathPoints[i + 1],
                                                strokeWidth = 3f * scale.coerceIn(0.5f, 2f)
                                            )
                                        }
                                    }
                                    pathPoints.clear()
                                }
                            }
                            x += stepX
                        }

                        for (i in 0 until pathPoints.size - 1) {
                            drawLine(
                                color = pathColor,
                                start = pathPoints[i],
                                end = pathPoints[i + 1],
                                strokeWidth = 6f
                            )

                        }
                    } else {
                        // Polar coordinates support
                        val angleStep = (2f * PI / (360f * scale)).toFloat().coerceAtMost(0.1f)
                        var angle = 0f
                        while (angle < 2f * PI) {
                            try {
                                val r =
                                    expression.evaluate(mapOf("u" to angle.toDouble(), "r" to 0.0))
                                        .toFloat()
                                if (r.isFinite()) {
                                    val xCartesian = r * cos(angle)
                                    val yCartesian = r * sin(angle)
                                    val canvasX = originX + xCartesian * step * scale
                                    val canvasY = originY - yCartesian * step * scale
                                    pathPoints.add(Offset(canvasX, canvasY))
                                }
                            } catch (e: Exception) {
                                // Handle discontinuities
                                if (pathPoints.size > 1) {
                                    for (i in 0 until pathPoints.size - 1) {
                                        drawLine(
                                            color = pathColor,
                                            start = pathPoints[i],
                                            end = pathPoints[i + 1],
                                            strokeWidth = 3f * scale.coerceIn(0.5f, 2f)
                                        )
                                    }
                                    pathPoints.clear()
                                }
                            }
                            angle += angleStep
                        }
                        for (i in 0 until pathPoints.size - 1) {
                            drawLine(
                                color = pathColor,
                                start = pathPoints[i],
                                end = pathPoints[i + 1],
                                strokeWidth = 6f
                            )
                        }
                    }
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

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Column() {
                state.functions.forEachIndexed { index, _ ->
                    FunctionField(
                        color = state.functionColors[index],
                        value = state.textFieldsContent[index],
                        onValueChange = { onTextFieldEdit(index, it) },
                        onColorClick = {
                            onSetCurrentIndex(index)
                            onToggledColorPicker()
                        },
                        onClearClick = { onFunctionRemoveTapped(index) }
                    )
                }
                VSpacer(6)
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center
                ) {
                    TextButton(
                        onClick = { onFunctionAddTapped() },
                    ) {
                        Text("Add Function")
                    }
                }
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
