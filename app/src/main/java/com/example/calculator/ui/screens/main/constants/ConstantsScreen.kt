package com.example.calculator.ui.screens.main.constants

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ButtonElevation
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.calculator.models.constantsMathematics
import com.example.calculator.models.constantsScience
import com.example.calculator.ui.screens.main.components.SearchDialog
import com.example.calculator.ui.screens.main.components.SideMenu
import com.example.calculator.ui.utils.VSpacer
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ConstantsScreen(
    viewModel: ConstantsScreenViewModel,
    navigateToMain: () -> Unit,
    navigateToUnitConversion: () -> Unit,
    navigateToTriangle: () -> Unit,
    navigateToEquations: () -> Unit,
    navigateToMatrix: () -> Unit,
) {
    val scrollState = rememberScrollState()
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val scope = rememberCoroutineScope()
    val state = viewModel.uiState.collectAsStateWithLifecycle().value
    val isSearchDialogVisible = remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        viewModel.uiEffect.collect { effect ->
            when (effect) {
                ConstantsScreenContract.Effect.ShowDialog -> {
                    isSearchDialogVisible.value = true
                }
            }
        }
    }

    SideMenu(
        navigateToMain = navigateToMain,
        navigateToUnitConversion = navigateToUnitConversion,
        navigateToTriangle = navigateToTriangle,
        navigateToConstants = { },
        navigateToEquations = navigateToEquations,
        navigateToMatrix = navigateToMatrix,
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
                    title = { Text(text = "Constants") }
                )
            },
            floatingActionButton = {
                Button(
                    onClick = { isSearchDialogVisible.value = true },
                    elevation = ButtonDefaults.elevatedButtonElevation(
                        defaultElevation = 8.dp,
                        pressedElevation = 8.dp,
                        hoveredElevation = 8.dp,
                        focusedElevation = 8.dp,
                        disabledElevation = 0.dp
                    )
                ) {
                    Icon(
                        imageVector = Icons.Default.Search,
                        contentDescription = "Search",
                        modifier = Modifier.padding(start = 0.dp)
                    )
                    Text("Search",  modifier = Modifier.padding(start = 8.dp), fontSize = 16.sp)
                }
            },
            modifier = Modifier.fillMaxWidth()
        ) { paddingValues ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
                    .padding(horizontal = 16.dp)
                    .verticalScroll(scrollState)
            ) {
                VSpacer(6)

                // --- Mathematics Constants ---
                Text("Mathematics", style = MaterialTheme.typography.titleMedium)
                HorizontalDivider(thickness = 1.dp)
                VSpacer(8)
                constantsMathematics.forEach { constant ->
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 4.dp)
                    ) {
                        Text(
                            text = constant.symbol,
                            modifier = Modifier.weight(2f)
                        )
                        Text(
                            text = constant.name,
                            modifier = Modifier.weight(8f)
                        )
                        Text(
                            text = String.format("%.6g", constant.value),
                            modifier = Modifier.weight(3f)
                        )
                    }
                }

                VSpacer(16)

                // --- Science Constants ---
                Text("Science", style = MaterialTheme.typography.titleMedium)
                HorizontalDivider(thickness = 1.dp)
                VSpacer(8)
                constantsScience.forEach { constant ->
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 4.dp)
                    ) {
                        Text(
                            text = constant.symbol,
                            modifier = Modifier.weight(2f)
                        )
                        Text(
                            text = constant.name,
                            modifier = Modifier.weight(7f)
                        )
                        Text(
                            text = String.format("%.6g", constant.value),
                            modifier = Modifier.weight(4f)
                        )
                    }
                }

                VSpacer(24)
            }
        }
    }

    if (isSearchDialogVisible.value) {
        SearchDialog(
            onInputChange = {},
            positiveAction = {
                viewModel.setEvent(
                    ConstantsScreenContract.Event.TappedPositiveButton(
                        value = it
                    )
                )
            },
            negativeAction = { isSearchDialogVisible.value = false }
        )
    }

}


@SuppressLint("ViewModelConstructorInComposable")
@Preview(showBackground = true)
@Composable
fun ConstantsScreenPreview() {
    val viewModel = ConstantsScreenViewModel()

    ConstantsScreen(
        navigateToMain = { },
        navigateToUnitConversion = { },
        navigateToTriangle = { },
        navigateToEquations = { },
        navigateToMatrix = { },
        viewModel = viewModel
    )
}