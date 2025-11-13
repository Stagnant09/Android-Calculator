package com.example.calculator.ui.screens.calculus.integral

import android.annotation.SuppressLint
import android.content.res.Configuration
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
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
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
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
            IntegralScreenContent(paddingValues = paddingValues)
        }
    }
}

@Composable
fun IntegralScreenContent(
    paddingValues: PaddingValues
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
                OutlinedTextField(
                    value = "",
                    onValueChange = { },
                    modifier = Modifier.size(width = 28.dp, height = 28.dp).offset(x = 110.dp, y = 36.dp)
                )
                OutlinedTextField(
                    value = "",
                    onValueChange = { },
                    modifier = Modifier.size(width = 28.dp, height = 28.dp).offset(x = 110.dp, y = 84.dp)
                )
                OutlinedTextField(
                    value = "",
                    onValueChange = { },
                    modifier = Modifier.size(width = 148.dp, height = 48.dp).offset(x = 150.dp, y = 50.dp)
                )
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
