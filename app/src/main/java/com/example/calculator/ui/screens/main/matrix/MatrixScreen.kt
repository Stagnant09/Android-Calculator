package com.example.calculator.ui.screens.main.matrix

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.IconButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MenuAnchorType
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.calculator.models.Matrix
import com.example.calculator.ui.screens.main.components.MatrixInput
import com.example.calculator.models.MatrixOperationType
import com.example.calculator.models.allOperations
import com.example.calculator.models.toLabel
import com.example.calculator.ui.screens.main.components.MatrixAlgebraRow
import com.example.calculator.ui.screens.main.components.MatrixDimensionsPickerModal
import com.example.calculator.ui.screens.main.components.MatrixResultModal
import com.example.calculator.ui.screens.main.components.RoundedRectangle
import com.example.calculator.ui.screens.main.components.ScreenBase
import com.example.calculator.ui.screens.main.components.SideMenu
import com.example.calculator.ui.theme.AppThemeCustomColors.colors
import com.example.calculator.ui.utils.HSpacer
import com.example.calculator.ui.utils.VSpacer

@Composable
fun MatrixScreen(
    viewModel: MatrixScreenViewModel,
    navigateToMain: () -> Unit,
    navigateToUnitConversion: () -> Unit,
    navigateToTriangle: () -> Unit,
    navigateToConstants: () -> Unit,
    navigateToEquations: () -> Unit,
    navigateToMatrix: () -> Unit,
) {
    val isModalOpenA = remember { mutableStateOf(false) }
    val isModalOpenB = remember { mutableStateOf(false) }
    val isResultModalOpen = remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        viewModel.uiEffect.collect { effect ->
            when (effect) {
                is MatrixScreenContract.Effect.ShowDimensionsModal -> {
                    when (effect.matrix.toInt()) {
                        0 -> isModalOpenA.value = true
                        1 -> isModalOpenB.value = true
                    }
                }

                is MatrixScreenContract.Effect.ShowResultModal -> {
                    isResultModalOpen.value = true
                }
            }
        }
    }

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
        navigateToMatrix = navigateToMatrix,
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

    if (isModalOpenA.value) {
        MatrixDimensionsPickerModal(
            showDialog = isModalOpenA.value,
            onDismissRequest = { isModalOpenA.value = false },
            initialRows = state.matrixA.rows,
            initialColumns = state.matrixA.columns,
            onConfirm = { rows, cols ->
                viewModel.setEvent(
                    MatrixScreenContract.Event.SetMatrixDimensions(
                        0,
                        rows,
                        cols
                    )
                )
                isModalOpenA.value = false
            }
        )
    }
    if (isModalOpenB.value) {
        MatrixDimensionsPickerModal(
            showDialog = isModalOpenB.value,
            onDismissRequest = { isModalOpenB.value = false },
            initialRows = state.matrixB.rows,
            initialColumns = state.matrixB.columns,
            onConfirm = { rows, cols ->
                viewModel.setEvent(
                    MatrixScreenContract.Event.SetMatrixDimensions(
                        1,
                        rows,
                        cols
                    )
                )
                isModalOpenB.value = false
            }
        )
    }
    if (isResultModalOpen.value) {
        MatrixResultModal(
            showDialog = isResultModalOpen.value,
            onDismissRequest = { isResultModalOpen.value = false },
            opertationType = state.currentOperation,
            matrix = state.matrixC,
            value = state.impVal
        )
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
    val addOrMultiply = remember { mutableStateOf("+") }

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
    VSpacer(20)
    Row(
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text("Matrix A", style = MaterialTheme.typography.titleMedium)
        HSpacer(16)
        RoundedRectangle(
            text = "${state.matrixA.rows}x${state.matrixA.columns}",
            height = 32.dp,
            width = 64.dp,
            radius = 16.dp,
            fontSize = 16.sp,
            verticalTextPadding = 6.dp,
            horizontalTextPadding = 16.dp,
            backgroundColor = Color(0xffe8e8e8),
            textColor = Color.Black,
            textAlign = TextAlign.Center
        )
        HSpacer(4)
        IconButton(
            onClick = {
                onEventSent(MatrixScreenContract.Event.OpenMatrixDimensionsModal(0, state.matrixA.rows, state.matrixA.columns))
            }
        ) {
            Icon(
                imageVector = Icons.Default.Edit,
                contentDescription = "Edit",
                tint = colors.pencilColor
            )
        }
    }
    VSpacer(6)
    MatrixInput(state.matrixA.rows, state.matrixA.columns, state.matrixA.elements) { i, j, v ->
        onEventSent(MatrixScreenContract.Event.TappedNumberField(0, i, j, v))
    }
    VSpacer(24)
    Row(
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text("Matrix B", style = MaterialTheme.typography.titleMedium)
        HSpacer(16)
        RoundedRectangle(
            text = "${state.matrixB.rows}x${state.matrixB.columns}$",
            height = 32.dp,
            width = 64.dp,
            radius = 16.dp,
            fontSize = 16.sp,
            verticalTextPadding = 6.dp,
            horizontalTextPadding = 16.dp,
            backgroundColor = Color(0xffe8e8e8),
            textColor = Color.Black,
            textAlign = TextAlign.Center
        )
        HSpacer(4)
        IconButton(
            onClick = {
                onEventSent(MatrixScreenContract.Event.OpenMatrixDimensionsModal(1, state.matrixB.rows, state.matrixB.columns))
            }
        ) {
            Icon(
                imageVector = Icons.Default.Edit,
                contentDescription = "Edit",
                tint = colors.pencilColor
            )
        }
    }
    VSpacer(6)
    MatrixInput(state.matrixB.rows, state.matrixB.columns, state.matrixB.elements) { i, j, v ->
        onEventSent(MatrixScreenContract.Event.TappedNumberField(1, i, j, v))
    }
    VSpacer(14)
    when (state.currentOperation) {
        MatrixOperationType.Binary.AddMultiply -> {
            MatrixAlgebraRow(
                lambda1 = state.coefA.toString(),
                lambda2 = state.coefB.toString(),
                operation = addOrMultiply.value,
                onLambda1Change = {
                    onEventSent(MatrixScreenContract.Event.TappedCoefField(0, if (it == "") 0.toFloat() else it.toFloat()))
                },
                onLambda2Change = {
                    onEventSent(MatrixScreenContract.Event.TappedCoefField(1, if (it == "") 0.toFloat() else it.toFloat()))
                },
                onOperationClick = {
                    addOrMultiply.value = if (addOrMultiply.value == "+") "*" else "+"
                },
                onEqualsClick = {
                    if (addOrMultiply.value == "+") {
                        onEventSent(MatrixScreenContract.Event.TappedEqualButtonAdd)
                    } else {
                        onEventSent(MatrixScreenContract.Event.TappedEqualButtonMultiply)
                    }
                }
            )
        }
        MatrixOperationType.Unary.Determinant -> {
            Row {
                Column {
                    OutlinedButton(
                        onClick = {}
                    ) {
                        Text("det(A)")
                    }
                }
                HSpacer(8)
                Column {
                    OutlinedButton(
                        onClick = {}
                    ) {
                        Text("det(B)")
                    }
                }

            }
        }
        MatrixOperationType.Unary.Inverse -> {
            Row {
                Column {
                    OutlinedButton(
                        onClick = {}
                    ) {
                        Text("inverse(A)")
                    }
                }
                HSpacer(8)
                Column {
                    OutlinedButton(
                        onClick = {}
                    ) {
                        Text("inverse(B)")
                    }
                }
            }
        }
        MatrixOperationType.Unary.Transpose -> {
            Row {
                Column {
                    OutlinedButton(
                        onClick = {}
                    ) {
                        Text("transpose(A)")
                    }
                }
                HSpacer(8)
                Column {
                    OutlinedButton(
                        onClick = {}
                    ) {
                        Text("transpose(B)")
                    }
                }
            }
        }
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
        navigateToEquations = { },
        navigateToMatrix = { }
    )
}