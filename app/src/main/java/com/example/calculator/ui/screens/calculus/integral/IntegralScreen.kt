package com.example.calculator.ui.screens.calculus.integral

import android.annotation.SuppressLint
import android.content.res.Configuration
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.calculator.navigation.AppRoute
import com.example.calculator.ui.components.CartesianGridCanvas
import com.example.calculator.ui.components.SideMenu
import com.example.calculator.ui.theme.AppTheme
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun IntegralScreen(
    viewModel: IntegralViewModel,
    onNavigate: (AppRoute) -> Unit
) {
    val scope = rememberCoroutineScope()
    val drawerState =
        rememberDrawerState(initialValue = androidx.compose.material3.DrawerValue.Closed)
    val state = viewModel.uiState.collectAsStateWithLifecycle().value

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
                    title = { Text(text = "Integral") },
                    actions = {

                    }
                )
            },
            modifier = Modifier.fillMaxWidth()
        ) { paddingValues ->
            IntegralScreenContent(paddingValues = paddingValues, state = state, onEventSent = {
                viewModel.setEvent(it)
            })
        }
    }
}

@Composable
fun IntegralScreenContent(
    paddingValues: PaddingValues,
    state: IntegralContract.State,
    onEventSent: (IntegralContract.Event) -> Unit
) {
    Column(modifier = Modifier.padding(paddingValues)) {
        Row(modifier = Modifier.weight(5f)) {
            CartesianGridCanvas(
                scale = 1f,
                offsetX = 0f,
                offsetY = 0f,
                step = 50f,
                onPan = { _, _ -> },
                onZoomIn = { },
                onZoomOut = { },
                onResetView = { },
                onDragStart = { },
                onDragEnd = { },
                onDrag = { _, _ -> }
            )
        }
        Row(modifier = Modifier.weight(4f)) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
            ) {
                Text(text = "I = ∫", modifier = Modifier.offset(x = 30.dp, y = 50.dp), fontSize = 44.sp)
                Box(
                    modifier = Modifier
                        .size(34.dp)
                        .offset(x = 110.dp, y = 36.dp)
                        .border(1.dp, Color(0xFFB388FF), RoundedCornerShape(4.dp)), // same violet border
                    contentAlignment = Alignment.Center
                ) {
                    BasicTextField(
                        value = state.upperIntegralValue,
                        onValueChange = {
                            onEventSent(IntegralContract.Event.UpdateUpperIntegralValue(it))
                        },
                        singleLine = true,
                        textStyle = TextStyle(
                            color = Color.White,
                            fontSize = 14.sp,
                            textAlign = TextAlign.Center
                        ),
                        modifier = Modifier.fillMaxSize(),
                        decorationBox = { innerTextField ->
                            Box(
                                modifier = Modifier.fillMaxSize(),
                                contentAlignment = Alignment.Center
                            ) {
                                innerTextField()
                            }
                        }
                    )
                }

                Box(
                    modifier = Modifier
                        .size(34.dp)
                        .offset(x = 110.dp, y = 84.dp)
                        .border(1.dp, Color(0xFFB388FF), RoundedCornerShape(4.dp)),
                    contentAlignment = Alignment.Center
                ) {
                    BasicTextField(
                        value = state.lowerIntegralValue,
                        onValueChange = {
                            onEventSent(IntegralContract.Event.UpdateLowerIntegralValue(it))
                        },
                        singleLine = true,
                        textStyle = TextStyle(
                            color = Color.White,
                            fontSize = 14.sp,
                            textAlign = TextAlign.Center
                        ),
                        modifier = Modifier.fillMaxSize(),
                        decorationBox = { innerTextField ->
                            Box(
                                modifier = Modifier.fillMaxSize(),
                                contentAlignment = Alignment.Center
                            ) {
                                innerTextField()
                            }
                        }
                    )
                }

                Box(
                    modifier = Modifier
                        .size(width = 148.dp, height = 48.dp)
                        .offset(x = 150.dp, y = 50.dp)
                        .border(1.dp, Color(0xFFB388FF), RoundedCornerShape(6.dp)), // same violet tone
                    contentAlignment = Alignment.Center
                ) {
                    BasicTextField(
                        value = state.expression,
                        onValueChange = {
                            onEventSent(IntegralContract.Event.UpdateIntegralExpression(it))
                        },
                        singleLine = true,
                        textStyle = TextStyle(
                            color = Color.White,
                            fontSize = 18.sp,
                            textAlign = TextAlign.Left
                        ),
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(horizontal = 4.dp), // optional: remove if you want zero padding
                        decorationBox = { innerTextField ->
                            Box(
                                modifier = Modifier.fillMaxSize(),
                                contentAlignment = Alignment.Center
                            ) {
                                innerTextField()
                            }
                        }
                    )
                }

                Text(text = "dx", modifier = Modifier.offset(x = 310.dp, y = 58.dp), fontSize = 34.sp)
            }
        }
    }
}

@SuppressLint("ViewModelConstructorInComposable")
@Preview(showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun IntegralScreenPreview() {
    val viewModel = IntegralViewModel()
    AppTheme {
        IntegralScreen(viewModel = viewModel, onNavigate = {})
    }
}
