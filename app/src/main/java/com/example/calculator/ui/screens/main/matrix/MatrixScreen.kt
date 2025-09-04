package com.example.calculator.ui.screens.main.matrix

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MenuAnchorType
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.calculator.models.MatrixInput
import com.example.calculator.models.MatrixOperationType
import com.example.calculator.models.allOperations
import com.example.calculator.models.toLabel
import com.example.calculator.ui.screens.main.components.ScreenBase
import com.example.calculator.ui.screens.main.components.SideMenu
import com.example.calculator.ui.utils.VSpacer

@Composable
fun MatrixScreen(
    viewModel: MatrixScreenViewModel,
    navigateToMain: () -> Unit,
    navigateToUnitConversion: () -> Unit,
    navigateToTriangle: () -> Unit,
    navigateToConstants: () -> Unit,
    navigateToEquations: () -> Unit
) {
    val scrollState = rememberScrollState()
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val scope = rememberCoroutineScope()

    val state = viewModel.uiState.collectAsStateWithLifecycle().value

    SideMenu(
        navigateToMain = navigateToMain,
        navigateToUnitConversion = navigateToUnitConversion,
        navigateToTriangle = navigateToTriangle,
        navigateToConstants = navigateToConstants,
        navigateToEquations = navigateToEquations,
        drawerState = drawerState
    ) {
        ScreenBase(
            scrollState = scrollState,
            drawerState = drawerState,
            scope = scope,
            title = "Matrix Algebra",
        ) {
            MatrixCalculator(
                onEventSent = { event ->
                    viewModel.setEvent(event)
                },
                state = state
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MatrixCalculator(
    state: MatrixScreenContract.State = MatrixScreenContract.State(
        currentOperation = MatrixOperationType.Binary.AddMultiply
    ),
    onEventSent: (MatrixScreenContract.Event) -> Unit
) {
    var expanded by remember { mutableStateOf(false) }

    Row(verticalAlignment = Alignment.CenterVertically) {
        ExposedDropdownMenuBox(
            expanded = expanded,
            onExpandedChange = { expanded = it },
        ) {
            TextField(
                modifier = Modifier.menuAnchor(MenuAnchorType.PrimaryNotEditable),
                value = toLabel(state.currentOperation),
                onValueChange = {},
                readOnly = true,
                singleLine = true,
                label = { Text("Operation") },
                trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded) },
                colors = ExposedDropdownMenuDefaults.textFieldColors(),
            )
            DropdownMenu(expanded = expanded, onDismissRequest = { }) {
                allOperations().forEach {
                    DropdownMenuItem(text = { Text(toLabel(it)) }, onClick = {
                        onEventSent(MatrixScreenContract.Event.TappedTopOperationButton(it))
                        expanded = false
                    })
                }
            }
        }
    }
    VSpacer(32)
    Text("Matrix A", style = MaterialTheme.typography.titleMedium)
    MatrixInput(state.matrix1.rows, state.matrix1.elements) { i, j, v ->
        onEventSent(MatrixScreenContract.Event.TappedNumberField(0, i, j, v))
    }
    VSpacer(24)
    Text("Matrix B", style = MaterialTheme.typography.titleMedium)
    MatrixInput(state.matrix2.rows, state.matrix2.elements) { i, j, v ->
        onEventSent(MatrixScreenContract.Event.TappedNumberField(1, i, j, v))
    }
}

@SuppressLint("ViewModelConstructorInComposable")
@Preview(showBackground = true)
@Composable
fun MatrixScreenPreview() {
    val viewModel = MatrixScreenViewModel()
    MatrixScreen(
        viewModel,
        navigateToMain = { },
        navigateToUnitConversion = { },
        navigateToTriangle = { },
        navigateToConstants = { },
        navigateToEquations = { }
    )
}