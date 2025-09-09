package com.example.calculator.ui.screens.main.triangleCalculator.interactive

import android.annotation.SuppressLint
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.nativeCanvas
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.unit.toSize
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.calculator.models.Vertices
import com.example.calculator.ui.screens.main.components.Grid
import com.example.calculator.ui.theme.AppTheme
import com.example.calculator.ui.utils.HSpacer
import com.example.calculator.utlis.toCanvas
import com.example.calculator.utlis.toModel
import kotlin.math.roundToInt

/** This composable contains a triangle display with interactive elements i.e.
 * draggable vertices. Below, there are non-editable text fields that display
 * the angles of the triangle, their respective trigonometric values, and other quantities
 * derived from the triangle.
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TriangleInteractive(
    goBack: () -> Unit,
    viewModel: TriangleInteractiveViewModel
) {
    val state by remember { viewModel.uiState }.collectAsStateWithLifecycle()
    var showDegrees by remember { mutableStateOf(true) }

    var vertices = remember {
        mutableStateOf(
            Vertices(
                Ax = -2.0,
                Ay = -1.0,
                Bx = 1.0,
                By = 0.0,
                Cx = 0.0,
                Cy = 1.0
            )
        )
    }

    Scaffold(
        topBar = {
            TopAppBar(
                navigationIcon = {
                    IconButton(onClick = goBack) {
                        Icon(imageVector = Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Go Back")
                    }
                },
                title = { Text("Interactive Triangle") },
                actions = {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Text(if (showDegrees) "Degrees" else "Radians", fontSize = 12.sp)
                        HSpacer(4)
                        Switch(
                            checked = showDegrees,
                            onCheckedChange = { showDegrees = it }
                        )
                    }
                }
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
        ) {
            // 1. The canvas that contains the interactive triangle. The canvas is filled
            // with a grid marking vertical and parallel lines with distance of 1 unit.
            // The background of the canvas is black. The grid lines are dark gray.
            // For example, the line y = 1 is dark gray, surrounded by a black background.
            // The triangle's vertices are draggable and can be moved around.
            // The triangle's vertices are represented by small red circles.
            // The triangle's edges are represented by light gray thick lines.
            // Next to the vertices, there are non-editable text fields that display the name of
            // the vertex and its coordinates. Similarly, next to the edges, there are non-editable
            // text fields that display the name of the edge.
            // The angles of the triangle are represented by non-editable text fields that display
            // their values in degrees OR radians (switchable by a Switch component, placed at the top
            // bar of the Scaffold).
            // The vertices are draggable. This means that by tapping on the circle of a vertex,
            // the vertex can be moved around to new coordinates. This movement has to be smooth,
            // and displayed in real time, along side with the correspondent change in the text fields.
            // For example, if the user drags the circle of vertex A to the right, then the text field
            // that displays the coordinates of A should be updated to the new coordinates, and the
            // text field that displays the angle of vertex A should be updated to the new angle etc.
            // Interactive means that the screen is in constant stand-by mode, to adapt to the user's
            // input. --- This part of the columns covers about half of the height of the screen

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f) // top half
                    .background(Color.Black)
            ) {
                TriangleCanvas(
                    modifier = Modifier.fillMaxSize(),
                    vertices = vertices,
                    showDegrees = showDegrees,
                    viewModel = viewModel
                )
            }

            Divider(color = Color.Gray)

            // 2. Below the interactive triangle, there are non-editable text fields that display
            // the angles of the triangle, their respective trigonometric values, and other quantities
            // derived from the triangle. The fields are placed in a grid-like layout, resembling
            // a spreadsheet or a table. Values displayed:
            // angles; sine; cosine; tangent; cotangent; secant; cosecant; edge lengths; area; perimeter
            // Coordinates of: vertices; centroid; incenter; circumcenter; orthocenter
            // All these values change in real time, as the user drags the vertices around.
            /// --- This part of the columns covers the other half of the height of the screen

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f) // bottom half
                    .padding(0.dp)
            ) {
                TriangleInfoTable(showDegrees = showDegrees, state = state)
            }
        }
    }
}

@Composable
fun TriangleCanvas(
    modifier: Modifier = Modifier,
    vertices: MutableState<Vertices>,
    showDegrees: Boolean,
    viewModel: TriangleInteractiveViewModel
) {
    val step = 50f // pixels per unit
    var draggingVertex by remember { mutableStateOf<String?>(null) }

    Box(
        modifier = modifier
            .pointerInput(Unit) {
                detectDragGestures(
                    onDragStart = { offset ->
                        val size = this.size.toSize()
                        val a = toCanvas(vertices.value.Ax, vertices.value.Ay, size, step)
                        val b = toCanvas(vertices.value.Bx, vertices.value.By, size, step)
                        val c = toCanvas(vertices.value.Cx, vertices.value.Cy, size, step)

                        val radius = 40f
                        when {
                            (offset - a).getDistance() <= radius -> draggingVertex = "A"
                            (offset - b).getDistance() <= radius -> draggingVertex = "B"
                            (offset - c).getDistance() <= radius -> draggingVertex = "C"
                        }
                    },
                    onDragEnd = {
                        draggingVertex = null
                        viewModel.setEvent(
                            TriangleInteractiveContract.Event.OnInputChanged(vertices.value)
                        )
                    },
                    onDragCancel = { draggingVertex = null },
                    onDrag = { change, _ ->
                        change.consume()
                        val size = this.size
                        val (mx, my) = toModel(change.position, size, step)
                        when (draggingVertex) {
                            "A" -> vertices.value = vertices.value.copy(Ax = mx, Ay = my)
                            "B" -> vertices.value = vertices.value.copy(Bx = mx, By = my)
                            "C" -> vertices.value = vertices.value.copy(Cx = mx, Cy = my)
                        }
                    }
                )
            }
    ) {
        Canvas(modifier = Modifier.fillMaxSize()) {
            // Grid
            for (x in 0..(size.width / step).toInt()) {
                val xPos = (x * step).roundToInt().toFloat()
                drawLine(Color.DarkGray, Offset(xPos, 0f), Offset(xPos, size.height))
            }
            for (y in 0..(size.height / step).toInt()) {
                val yPos = (y * step).roundToInt().toFloat()
                drawLine(Color.DarkGray, Offset(0f, yPos), Offset(size.width, yPos))
            }

            // Vertices in canvas space
            val a = toCanvas(vertices.value.Ax, vertices.value.Ay, size, step)
            val b = toCanvas(vertices.value.Bx, vertices.value.By, size, step)
            val c = toCanvas(vertices.value.Cx, vertices.value.Cy, size, step)

            // Edges
            drawLine(Color.LightGray, a, b, strokeWidth = 6f)
            drawLine(Color.LightGray, b, c, strokeWidth = 6f)
            drawLine(Color.LightGray, c, a, strokeWidth = 6f)

            // Vertices
            drawCircle(Color.Red, radius = 16f, center = a)
            drawCircle(Color.Red, radius = 16f, center = b)
            drawCircle(Color.Red, radius = 16f, center = c)

            // Decide placement for each vertex
            fun labelPosition(label: String, position: Offset, size: Size, vertices: Map<String, Offset>): Pair<Float, Float> {
                val x = position.x
                val y = position.y

                val westernmost = vertices.minBy { it.value.x }.key
                val easternmost = vertices.maxBy { it.value.x }.key
                val northernmost = vertices.minBy { it.value.y }.key
                val southernmost = vertices.maxBy { it.value.y }.key

                var dx = 25f
                var dy = -25f

                when (label) {
                    westernmost -> { dx = -90f; dy = 0f }
                    easternmost -> { dx = 90f; dy = 0f }
                    northernmost -> { dx = 0f; dy = -90f }
                    southernmost -> { dx = 0f; dy = 90f }
                }

                return Pair(x + dx, y + dy)
            }

            val verticesCanvas = mapOf(
                "A" to a,
                "B" to b,
                "C" to c
            )

            fun drawVertexLabel(label: String, canvasPos: Offset, modelX: Double, modelY: Double) {
                val paint = android.graphics.Paint().apply {
                    color = android.graphics.Color.WHITE
                    textSize = 24f
                    textAlign = android.graphics.Paint.Align.CENTER
                    isAntiAlias = true
                }

                val (lx, ly) = labelPosition(label, canvasPos, size, verticesCanvas)
                drawContext.canvas.nativeCanvas.drawText(
                    "$label(%.3f, %.3f)".format(modelX, modelY),
                    lx, ly, paint
                )
            }

            // math coordinates for display, canvas for placement
            drawVertexLabel("A", a, vertices.value.Ax, vertices.value.Ay)
            drawVertexLabel("B", b, vertices.value.Bx, vertices.value.By)
            drawVertexLabel("C", c, vertices.value.Cx, vertices.value.Cy)
        }
    }
}


@Composable
fun TriangleInfoTable(
    showDegrees: Boolean,
    state: TriangleInteractiveContract.State,
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            //.background(Color.White)
            .padding(0.dp)
    ) {

        val angleA = if (showDegrees) "%.5f\u00B0".format(state.angleA * 180 / Math.PI)
        else "%.5f".format(state.angleA)
        val angleB = if (showDegrees) "%.5f\u00B0".format(state.angleB * 180 / Math.PI)
        else "%.5f".format(state.angleB)
        val angleC = if (showDegrees) "%.5f\u00B0".format(state.angleC * 180 / Math.PI)
        else "%.5f".format(state.angleC)

        val rows = listOf(
            listOf("Angle A", angleA, "sin(A)", "%.5f".format(state.sinA)),
            listOf("cos(A)", "%.5f".format(state.cosA), "tan(A)", "%.5f".format(state.tanA)),
            listOf("Angle B", angleB, "sin(B)", "%.5f".format(state.sinB)),
            listOf("cos(B)", "%.5f".format(state.cosB), "tan(B)", "%.5f".format(state.tanB)),
            listOf("Angle C", angleC, "sin(C)", "%.5f".format(state.sinC)),
            listOf("cos(C)", "%.5f".format(state.cosC), "tan(C)", "%.5f".format(state.tanC)),
            listOf("Side a", "%.5f".format(state.sideA), "Side b", "%.5f".format(state.sideB)),
            listOf("Side c", "%.5f".format(state.sideC), "Perimeter", "%.5f".format(state.perimeter)),
            listOf("Area", "%.5f".format(state.area), "Centroid", "(%.1f, %.1f)".format(state.centroid.first, state.centroid.second)),
            listOf("Incenter", "(%.1f, %.1f)".format(state.incenter.first, state.incenter.second),
                "Circumcenter", "(%.1f, %.1f)".format(state.circumcenter.first, state.circumcenter.second)),
            listOf("Orthocenter", "(%.1f, %.1f)".format(state.orthocenter.first, state.orthocenter.second), "", "")
        )

        Grid(
            rows = rows.size,
            columns = 4,
            content = buildList {
                // Data rows
                rows.forEach { row ->
                    row.forEach { cell ->
                        add { TableCell(text = cell) }
                    }
                }
            }
        )
    }
}

@Composable
fun TableCell(text: String, bold: Boolean = false) {
    Box(
        modifier = Modifier
            .border(1.dp, color = Color.Gray)
            .padding(3.dp)
            .fillMaxWidth(),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = text,
            fontWeight = if (bold) FontWeight.Bold else FontWeight.Normal,
            fontSize = 12.sp
        )
    }
}


@SuppressLint("ViewModelConstructorInComposable")
@Preview(showBackground = true)
@Composable
fun TriangleInteractivePreview() {
    val viewModel = TriangleInteractiveViewModel()
    AppTheme {
        TriangleInteractive(
            viewModel = viewModel,
            goBack = {}
        )
    }
}