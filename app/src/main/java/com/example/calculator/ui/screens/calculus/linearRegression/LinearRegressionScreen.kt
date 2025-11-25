import android.util.Log
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
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
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.calculator.navigation.AppRoute
import com.example.calculator.ui.components.CartesianGridCanvas
import com.example.calculator.ui.components.SideMenu
import com.example.calculator.ui.screens.calculus.linearRegression.LinearRegressionViewModel
import com.example.calculator.utlis.canvasToCartesian
import com.example.calculator.utlis.cartesianToCanvas
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LinearRegressionScreen(
    viewModel: LinearRegressionViewModel,
    onNavigate: (AppRoute) -> Unit
) {
    val state = viewModel.uiState.collectAsStateWithLifecycle().value
    val drawerState = rememberDrawerState(DrawerValue.Closed)
    val scope = rememberCoroutineScope()

    val offsetX = remember { mutableStateOf(0f) }
    val offsetY = remember { mutableStateOf(0f) }
    val scale = remember { mutableStateOf(1f) }
    val step = 50f

    var dragIndex: Int? = remember { null }

    SideMenu(
        drawerState = drawerState,
        onNavigate = onNavigate
    ) {
        Scaffold(
            topBar = {
                TopAppBar(
                    title = { Text("Linear Regression") },
                    navigationIcon = {
                        IconButton(onClick = { scope.launch { drawerState.open() } }) {
                            Icon(Icons.Default.Menu, contentDescription = "Menu")
                        }
                    }
                )
            }
        ) { paddingValues ->
            Column(Modifier.padding(paddingValues)) {
                Row(modifier = Modifier.weight(7f)){
                    CartesianGridCanvas(
                        scale = scale.value,
                        offsetX = offsetX.value,
                        offsetY = offsetY.value,
                        step = step,
                        onPan = { dx, dy ->
                            offsetX.value += dx
                            offsetY.value += dy
                        },
                        onZoomIn = { scale.value *= 1.3f },
                        onZoomOut = { scale.value /= 1.3f },
                        onResetView = {
                            offsetX.value = 0f
                            offsetY.value = 0f
                            scale.value = 1f
                        },
                        onTap = { tapX, tapY, originX, originY ->
                            // Check if tapped on existing point
                            val hitIndex = state.points.indexOfFirst {
                                val (cx, cy) = cartesianToCanvas(
                                    it.x, it.y, originX, originY, step, scale.value
                                )
                                (Offset(cx, cy).minus(Offset(tapX, tapY))).getDistance() < 25f
                            }

                            if (hitIndex != -1) {
                                viewModel.setEvent(LinearRegressionContract.Event.SelectPoint(hitIndex))
                            } else {
                                // Convert to cartesian and add new point
                                val (x, y) = canvasToCartesian(tapX, tapY, originX, originY, step, scale.value)
                                viewModel.setEvent(LinearRegressionContract.Event.AddPoint(x, y))
                            }
                        },
                        onDragStart = { x, y, originX, originY ->
                            // Select point for dragging
                            dragIndex = state.points.indexOfFirst {
                                val (cx, cy) = cartesianToCanvas(
                                    it.x, it.y, originX, originY, step, scale.value
                                )
                                (Offset(cx, cy).minus(Offset(x, y))).getDistance() < 25f
                            }.takeIf { it != -1 }
                        },
                        onDrag = { x, y, originX, originY ->
                            dragIndex?.let { index ->
                                val (cx, cy) = canvasToCartesian(x, y, originX, originY, step, scale.value)
                                viewModel.setEvent(LinearRegressionContract.Event.MovePoint(index, cx, cy))
                            }
                        },
                        onDragEnd = {
                            dragIndex = null
                        }
                    ) { canvas, originX, originY ->
                        // Draw points
                        state.points.forEachIndexed { i, p ->
                            val (cx, cy) = cartesianToCanvas(p.x, p.y, originX, originY, step, scale.value)
                            canvas.drawCircle(
                                color = if (i == state.selectedPointIndex) Color.Yellow else Color.Red,
                                radius = 10f,
                                center = Offset(cx, cy)
                            )
                        }

                        // Draw regression line
                        state.regression?.let { r ->
                            val x1 = -10f
                            val x2 = 10f
                            val y1 = r.a * x1 + r.b
                            val y2 = r.a * x2 + r.b

                            val (sx1, sy1) = cartesianToCanvas(x1, y1, originX, originY, step, scale.value)
                            val (sx2, sy2) = cartesianToCanvas(x2, y2, originX, originY, step, scale.value)

                            canvas.drawLine(
                                color = Color.Blue,
                                start = Offset(sx1, sy1),
                                end = Offset(sx2, sy2),
                                strokeWidth = 4f
                            )
                        }
                    }
                }

                // Regression text
                Row(modifier = Modifier.weight(4f)){
                    state.regression?.let { r ->
                        Column(modifier = Modifier.padding(12.dp)) {
                            Text("y = ${r.a}x + ${r.b}")
                            Text("Correlation: ${r.correlation}")
                            Text("Covariance: ${r.covariance}")
                        }
                    }
                }

                // Delete button
                Row(modifier = Modifier.weight(2f)){
                    if (state.selectedPointIndex != null) {
                        Log.d("LinearRegressionScreen", "Selected point index: ${state.selectedPointIndex}")
                        Text("X Remove", modifier = Modifier
                            .padding(16.dp)
                            .clickable {
                                viewModel.setEvent(LinearRegressionContract.Event.RemoveSelectedPoint)
                            }
                        )
                    }
                }
            }
        }
    }
}
