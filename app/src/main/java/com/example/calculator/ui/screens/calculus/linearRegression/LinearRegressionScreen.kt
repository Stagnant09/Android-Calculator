import androidx.compose.foundation.layout.Column
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
import androidx.compose.ui.graphics.Paint
import com.example.calculator.navigation.AppRoute
import com.example.calculator.ui.components.CartesianGridCanvas
import com.example.calculator.ui.components.SideMenu
import com.example.calculator.utlis.cartesianToCanvas
import com.example.calculator.utlis.scalePoint
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LinearRegressionScreen(
    viewModel: LinearRegressionViewModel,
    onNavigate: (AppRoute) -> Unit
) {
    val drawerState = rememberDrawerState(DrawerValue.Closed)
    val scope = rememberCoroutineScope()
    val offsetX = remember { mutableStateOf(0f) }
    val offsetY = remember { mutableStateOf(0f) }
    val scale = remember { mutableStateOf(1f) }
    val step = 50f

    SideMenu(
        drawerState = drawerState,
        onNavigate = onNavigate
    ) {
        Scaffold(
            topBar = {
                TopAppBar(
                    title = {
                        Text("Linear Regression")
                    },
                    navigationIcon = {
                        IconButton(onClick = {
                            scope.launch {
                                drawerState.open()
                            }
                        }) {
                            Icon(Icons.Default.Menu, contentDescription = "Menu")
                        }
                    }
                )
            }
        ) { paddingValues ->
            Column(Modifier.padding(paddingValues)) {
                CartesianGridCanvas(
                    scale = scale.value,
                    offsetX = offsetX.value,
                    offsetY = offsetY.value,
                    step = step,
                    onPan = { dragAmountX, dragAmountY ->
                        // move by (dragAmountX, dragAmountY)
                        offsetX.value += dragAmountX
                        offsetY.value += dragAmountY
                    },
                    onZoomIn = {
                        scale.value *= 1.3f
                    },
                    onZoomOut = {
                        scale.value /= 1.3f
                    },
                    onResetView = {
                        offsetX.value = 0f
                        offsetY.value = 0f
                        scale.value = 1f
                    },
                    onDragStart = {},
                    onDragEnd = {},
                    onDrag = { _, _ ->

                    },
                ) { canvas, originX, originY ->
                    // draw a filled circle at (1,1) - cartesian
                    val (x, y) = cartesianToCanvas(1f, 1f, originX, originY, step, scale.value)
                    canvas.drawCircle(
                        color = Color.Red,
                        radius = 10f,
                        center = Offset(x, y)
                    )
                }
                // TODO: Place linear regression result here
            }
        }
    }
}