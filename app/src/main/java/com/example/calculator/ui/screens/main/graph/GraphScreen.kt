package com.example.calculator.ui.screens.main.graph

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.rememberDrawerState
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Paint
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.graphics.nativeCanvas
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.calculator.R
import com.example.calculator.models.GraphMode
import com.example.calculator.navigation.AppRoute
import com.example.calculator.ui.components.FunctionSelectionColumn
import com.example.calculator.ui.components.ModificationDialog
import com.example.calculator.ui.components.RemoveButton
import com.example.calculator.ui.components.SideMenu
import com.example.calculator.ui.theme.AppThemeCustomColors.colors
import com.example.calculator.ui.utils.HSpacer
import kotlinx.coroutines.launch
import kotlin.math.abs
import kotlin.math.sqrt

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GraphScreen(
    viewModel: GraphViewModel = viewModel(),
    onNavigate: (AppRoute) -> Unit
) {
    val state by viewModel.uiState.collectAsStateWithLifecycle()
    val scope = rememberCoroutineScope()
    val drawerState = rememberDrawerState(DrawerValue.Closed)
    val sheetState = rememberModalBottomSheetState(
        skipPartiallyExpanded = false
    )

    val colors = listOf(
        colors.nodeGray,
        colors.nodeOrange,
        colors.nodeGreen,
        colors.nodeCyan,
        colors.nodePurple
    )

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
                    title = {
                        Text(text = "Graph Solver")
                    }
                )
            },
            floatingActionButton = {
                if (state.selectedNodeId != null) {
                    RemoveButton {
                        viewModel.setEvent(GraphScreenContract.Event.RemoveNode(state.selectedNodeId!!))
                    }
                }
                if (state.selectedEdge != null) {
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(16.dp), // padding from screen edges
                        contentAlignment = Alignment.BottomEnd // align the Row to bottom right
                    ) {
                        Row(
                            horizontalArrangement = Arrangement.spacedBy(8.dp), // spacing between buttons
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            FloatingActionButton(
                                onClick = { viewModel.setEvent(GraphScreenContract.Event.TappedPencilButon) },
                                containerColor = Color(220, 220, 220, 255)
                            ) {
                                Icon(
                                    imageVector = Icons.Default.Edit,
                                    contentDescription = "Edit",
                                    modifier = Modifier.size(24.dp),
                                    tint = Color.Black
                                )
                            }
                            RemoveButton {
                                viewModel.setEvent(GraphScreenContract.Event.RemoveEdge(state.selectedEdge!!))
                            }
                        }
                    }
                }
            },
            modifier = Modifier.fillMaxWidth()
        ) { paddingValues ->

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
            ) {
                // Mode Switch Buttons
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp)
                        .height(90.dp),
                    horizontalArrangement = Arrangement.Center
                ) {
                    Button(
                        onClick = {
                            viewModel.setEvent(
                                GraphScreenContract.Event.SwitchMode(
                                    GraphMode.EditNodes
                                )
                            )
                        },
                        colors = ButtonDefaults.buttonColors(
                            containerColor = if (state.mode == GraphMode.EditNodes) Color.Green else Color.Gray
                        )
                    ) { Text("Edit Nodes") }

                    HSpacer(8)

                    Button(
                        onClick = {
                            viewModel.setEvent(
                                GraphScreenContract.Event.SwitchMode(
                                    GraphMode.EditEdges
                                )
                            )
                        },
                        colors = ButtonDefaults.buttonColors(
                            containerColor = if (state.mode == GraphMode.EditEdges) Color.Green else Color.Gray
                        )
                    ) { Text("Edit Edges") }

                    HSpacer(8)

                    Button(
                        onClick = {
                            viewModel.setEvent(GraphScreenContract.Event.EnableBottomSheet)
                        },
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color(234, 53, 66, 255)
                        )
                    ) { Icon(
                        painter = painterResource(id = R.drawable.function),
                        contentDescription = "Function",
                        tint = Color.White
                    ) }
                }

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(500.dp)
                ) {
                    // Graph Canvas
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                    ) {
                        Canvas(
                            modifier = Modifier
                                .fillMaxSize()
                                .pointerInput(state.mode, state.nodes, state.edges) {
                                    detectTapGestures { offset ->
                                        when (state.mode) {
                                            GraphMode.EditNodes -> {
                                                val tappedNode =
                                                    state.nodes.find { (it.position - offset).getDistance() <= 50f }
                                                if (tappedNode != null) {
                                                    viewModel.setEvent(
                                                        GraphScreenContract.Event.SelectNode(
                                                            tappedNode.id
                                                        )
                                                    )
                                                } else {
                                                    viewModel.setEvent(
                                                        GraphScreenContract.Event.AddNode(
                                                            offset
                                                        )
                                                    )
                                                }
                                            }

                                            GraphMode.EditEdges -> {
                                                val tappedNode =
                                                    state.nodes.find { (it.position - offset).getDistance() <= 50f }
                                                tappedNode?.let {
                                                    viewModel.setEvent(
                                                        GraphScreenContract.Event.StartEdge(
                                                            it.id,
                                                            offset
                                                        )
                                                    )
                                                }
                                                val tappedEdge = state.edges.find { edge ->
                                                    val fromNode =
                                                        state.nodes.find { it.id == edge.from }
                                                            ?: return@find false
                                                    val toNode =
                                                        state.nodes.find { it.id == edge.to }
                                                            ?: return@find false
                                                    offset.distanceToLineSegment(
                                                        fromNode.position,
                                                        toNode.position
                                                    ) <= 20f
                                                }

                                                tappedEdge?.let {
                                                    viewModel.setEvent(
                                                        GraphScreenContract.Event.SelectEdge(it)
                                                    )
                                                }
                                            }
                                        }
                                    }
                                }
                                .pointerInput(state.mode, state.nodes, state.edges) {
                                    detectDragGestures(
                                        onDragStart = { offset ->
                                            if (state.mode == GraphMode.EditNodes) {
                                                // Check if dragging a node
                                                val node =
                                                    state.nodes.find { (it.position - offset).getDistance() <= 50f }
                                                node?.let {
                                                    viewModel.setEvent(
                                                        GraphScreenContract.Event.StartDragNode(
                                                            it.id
                                                        )
                                                    )
                                                }
                                            } else if (state.mode == GraphMode.EditEdges) {
                                                // Edge drag
                                                val node =
                                                    state.nodes.find { (it.position - offset).getDistance() <= 50f }
                                                node?.let {
                                                    viewModel.setEvent(
                                                        GraphScreenContract.Event.StartEdge(
                                                            it.id,
                                                            offset
                                                        )
                                                    )
                                                }
                                            }
                                        },
                                        onDrag = { change, dragAmount ->
                                            if (state.mode == GraphMode.EditNodes && state.selectedNodeId != null) {
                                                val node =
                                                    state.nodes.find { it.id == state.selectedNodeId }
                                                        ?: return@detectDragGestures
                                                viewModel.setEvent(
                                                    GraphScreenContract.Event.DragNode(
                                                        node.id,
                                                        node.position + dragAmount * 5F
                                                    )
                                                )
                                            } else if (state.mode == GraphMode.EditEdges && state.draggingEdgeFrom != null) {
                                                viewModel.setEvent(
                                                    GraphScreenContract.Event.UpdateDraggingEdge(
                                                        change.position
                                                    )
                                                )
                                            }
                                        },
                                        onDragEnd = {
                                            if (state.mode == GraphMode.EditEdges) {
                                                // Capture delegated properties in local variables
                                                val draggingFrom = state.draggingEdgeFrom
                                                val draggingPos = state.draggingEdgePosition

                                                if (draggingFrom != null) {
                                                    // Find target node
                                                    val targetNode = state.nodes.find {
                                                        (it.position - (draggingPos
                                                            ?: Offset.Zero)).getDistance() <= 50f
                                                    }
                                                    targetNode?.let {
                                                        viewModel.setEvent(
                                                            GraphScreenContract.Event.AddEdge(
                                                                draggingFrom,  // safe now
                                                                it.id,
                                                                1f
                                                            )
                                                        )
                                                    }
                                                    viewModel.setEvent(GraphScreenContract.Event.EndEdgeDrag)
                                                }
                                            }
                                        }
                                    )
                                }
                        ) {
                            // Draw edges
                            state.edges.forEach { edge ->
                                val fromNode =
                                    state.nodes.find { it.id == edge.from } ?: return@forEach
                                val toNode = state.nodes.find { it.id == edge.to } ?: return@forEach

                                val start = fromNode.position
                                val end = toNode.position

                                // Draw the line
                                drawLine(
                                    color = if (state.selectedEdge == edge) Color.Red
                                    else if (edge.from in state.shortestPath && edge.to in state.shortestPath) Color.Green
                                    else state.edges.find { it == edge }?.color ?: Color.Gray,
                                    start = start,
                                    end = end,
                                    strokeWidth = 8f
                                )

                                // Compute midpoint
                                val mid = Offset((start.x + end.x) / 2f, (start.y + end.y) / 2f)

                                // Offset the label slightly to avoid overlapping nodes
                                val offset = 45f
                                val direction = (end - start).let { diff ->
                                    if (abs(diff.y) > abs(diff.x)) Offset(
                                        offset,
                                        0f
                                    )
                                    else Offset(0f, offset)
                                }
                                val labelPos = mid + direction

                                // Draw the weight
                                val text = edge.weight.toString()

// Create the Paint for text
                                val textPaint = android.graphics.Paint().apply {
                                    color = android.graphics.Color.WHITE // text color
                                    textSize = 48f
                                    textAlign = android.graphics.Paint.Align.CENTER
                                    isAntiAlias = true
                                }

// Measure text bounds first
                                val textBounds = android.graphics.Rect()
                                textPaint.getTextBounds(text, 0, text.length, textBounds)

// Compute background rectangle
                                val padding = 12f
                                val bgLeft = labelPos.x - textBounds.width() / 2f - padding
                                val bgTop = labelPos.y + textBounds.top - padding
                                val bgRight = labelPos.x + textBounds.width() / 2f + padding
                                val bgBottom = labelPos.y + textBounds.bottom + padding

// Draw background
                                val bgPaint = android.graphics.Paint().apply {
                                    color = android.graphics.Color.argb(
                                        200,
                                        50,
                                        50,
                                        50
                                    ) // translucent dark background
                                    style = android.graphics.Paint.Style.FILL
                                    isAntiAlias = true
                                }
                                drawContext.canvas.nativeCanvas.drawRoundRect(
                                    bgLeft,
                                    bgTop,
                                    bgRight,
                                    bgBottom,
                                    16f, // corner radius
                                    16f,
                                    bgPaint
                                )

// Finally, draw text on top
                                drawContext.canvas.nativeCanvas.drawText(
                                    text,
                                    labelPos.x,
                                    labelPos.y,
                                    textPaint
                                )

                            }

                            // Draw nodes
                            state.nodes.forEach { node ->
                                drawCircle(
                                    color = if (node.id == state.selectedNodeId) Color.Green else Color.Blue,
                                    radius = 50f,
                                    center = node.position
                                )

                                drawContext.canvas.nativeCanvas.drawText(
                                    node.id.toString(),
                                    node.position.x,
                                    node.position.y + 12f,
                                    Paint().asFrameworkPaint().apply {
                                        color = android.graphics.Color.WHITE
                                        textSize = 44f
                                        textAlign = android.graphics.Paint.Align.CENTER
                                    }
                                )
                            }

                            // Draw in-progress edge
                            val draggingEdgeFrom = state.draggingEdgeFrom
                            val draggingEdgePos = state.draggingEdgePosition

                            if (draggingEdgeFrom != null && draggingEdgePos != null) {
                                val fromNode =
                                    state.nodes.find { it.id == draggingEdgeFrom } ?: return@Canvas
                                drawLine(
                                    color = Color.Black,
                                    start = fromNode.position,
                                    end = draggingEdgePos,
                                    strokeWidth = 4f,
                                    pathEffect = PathEffect.cornerPathEffect(5f)
                                )
                            }
                        }
                    }
                }
            }
        }
    }

    if (state.isEdgeBeingModified && state.selectedEdge != null) {
        ModificationDialog(
            initialValue = state.selectedEdge!!.weight,
            onConfirm = { newWeight, newColorIndex ->
                val newColor = colors[newColorIndex]

                viewModel.setEvent(
                    GraphScreenContract.Event.ConfirmEdgeWeight(
                        state.selectedEdge,
                        newWeight,
                        newColor
                    )
                )
            },
            onCancel = {
                viewModel.setEvent(GraphScreenContract.Event.DismissDialog)
            },
            label = "Edge Weight"
        )
    }

    if (state.isBottomSheetEnabled) {
        ModalBottomSheet(
            onDismissRequest = {
                viewModel.setEvent(GraphScreenContract.Event.DisableBottomSheet)
            }, // Called when user dismisses the sheet
            sheetState = sheetState,
        ) {
            // The content of the bottom sheet
            FunctionSelectionColumn(
                onFunctionSelected = {
                    // 1. Execute the function selection logic


                    // 2. Dismiss the bottom sheet immediately after selection
                    viewModel.setEvent(GraphScreenContract.Event.DisableBottomSheet)
                }
            )
        }
    }

}

// Helper function for Offset distance
fun Offset.getDistance(): Float = sqrt(this.x * this.x + this.y * this.y)
private fun Offset.distanceToLineSegment(start: Offset, end: Offset): Float {
    val lineLengthSquared = (end - start).getDistanceSquared()
    if (lineLengthSquared == 0f) return (this - start).getDistance()

    var t = ((this - start).dot(end - start)) / lineLengthSquared
    t = t.coerceIn(0f, 1f)
    val projection = start + (end - start) * t
    return (this - projection).getDistance()
}

// Offset helpers
private fun Offset.getDistanceSquared(): Float = this.x * this.x + this.y * this.y
private fun Offset.dot(other: Offset): Float = this.x * other.x + this.y * other.y
