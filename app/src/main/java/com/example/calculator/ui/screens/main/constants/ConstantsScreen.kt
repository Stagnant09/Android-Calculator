package com.example.calculator.ui.screens.main.constants

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
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.calculator.models.constantsMathematics
import com.example.calculator.models.constantsScience
import com.example.calculator.ui.screens.main.components.SideMenu
import com.example.calculator.ui.utils.VSpacer
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ConstantsScreen(
    navigateToMain: () -> Unit,
    navigateToUnitConversion: () -> Unit,
    navigateToTriangle: () -> Unit,
    navigateToEquations: () -> Unit
) {
    val scrollState = rememberScrollState()
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val scope = rememberCoroutineScope()

    SideMenu(
        navigateToMain = navigateToMain,
        navigateToUnitConversion = navigateToUnitConversion,
        navigateToTriangle = navigateToTriangle,
        navigateToConstants = { },
        navigateToEquations = navigateToEquations,
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
}


@Preview(showBackground = true)
@Composable
fun ConstantsScreenPreview() {
    ConstantsScreen(
        navigateToMain = { },
        navigateToUnitConversion = { },
        navigateToTriangle = { },
        navigateToEquations = { }
    )
}