package com.example.calculator.ui.screens.main

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.calculator.models.GridOrientation
import com.example.calculator.models.OperationType
import com.example.calculator.ui.screens.main.components.ExpressionDisplay
import com.example.calculator.ui.screens.main.components.Grid
import com.example.calculator.ui.screens.main.components.MainScreenContract
import com.example.calculator.ui.screens.main.components.OperationButton
import com.example.calculator.ui.theme.AppTheme
import com.example.calculator.utlis.symbol

@Composable
fun MainScreen(viewmodel: MainScreenViewmodel) {
    val state = viewmodel.uiState.collectAsStateWithLifecycle().value

    var selectedTabIndex by remember { mutableStateOf(0) }
    val tabTitles = listOf("Standard", "Scientific", "Programming")

    Column(
        modifier = Modifier
            .fillMaxHeight()
    ) {
        Spacer(
            modifier = Modifier.height(30.dp).fillMaxWidth().background(Color.Black)
        )
        TabRow(
            selectedTabIndex = selectedTabIndex,
            modifier = Modifier.fillMaxWidth()
        ) {
            tabTitles.forEachIndexed { index, title ->
                Tab(
                    text = { Text(title) },
                    selected = selectedTabIndex == index,
                    onClick = { selectedTabIndex = index }
                )
            }
        }

        Row(
            modifier = Modifier
                .weight(2F)
                .background(Color(0xffe8e8e8))
        ) {
            ExpressionDisplay(
                previousValueAndOperationSymbol = if (state.firstOperation || state.currentOperation == null) "" else "${state.value1} ${symbol(state.currentOperation!!)}",
                currentValue = if (state.firstOperation || state.currentOperation == null) state.value1.toString() else state.value2.toString(),
                modifier = Modifier.fillMaxHeight()
            )
        }
        Row {
            Spacer(modifier = Modifier.height(50.dp))
        }
        Row(
            modifier = Modifier
                .weight(3F)
                .fillMaxWidth()
                .align(Alignment.CenterHorizontally)
        ) {
            val gridContent = when (selectedTabIndex) {
                0 -> operationsGrid( // Standard
                    tappedOperationButton = { viewmodel.setEvent(MainScreenContract.Event.TappedOperationButton(it)) },
                    tappedNumberButton = { viewmodel.setEvent(MainScreenContract.Event.TappedNumberButton(it)) },
                    tappedEqualButton = { viewmodel.setEvent(MainScreenContract.Event.TappedEqualButton) },
                    tappedClearButton = { viewmodel.setEvent(MainScreenContract.Event.TappedClearButton) },
                    tappedDecimalButton = { viewmodel.setEvent(MainScreenContract.Event.TappedDecimalButton) }
                )
                1 -> scientificGrid( // 🔬 Scientific
                    tappedOperationButton = { viewmodel.setEvent(MainScreenContract.Event.TappedOperationButton(it)) }
                )
                2 -> programmingGrid( // 💻 Programming
                    tappedOperationButton = { viewmodel.setEvent(MainScreenContract.Event.TappedOperationButton(it)) },
                    tappedClearButton = { viewmodel.setEvent(MainScreenContract.Event.TappedClearButton) },
                    tappedNumberButton = { viewmodel.setEvent(MainScreenContract.Event.TappedNumberButton(it)) },
                    tappedEqualButton = { viewmodel.setEvent(MainScreenContract.Event.TappedEqualButton) }
                )
                else -> emptyList()
            }

            Grid(
                rows = 5,
                columns = 5,
                orientation = GridOrientation.DOWN_TO_UP_RIGHT_TO_LEFT,
                modifier = Modifier.fillMaxHeight(),
                content = gridContent
            )
        }
    }
}

private fun operationsGrid(
    tappedOperationButton: (OperationType) -> Unit,
    tappedNumberButton: (Float) -> Unit,
    tappedEqualButton: () -> Unit,
    tappedClearButton: () -> Unit,
    tappedDecimalButton: () -> Unit
) : List<@Composable () -> Unit> {
    return listOf(
        { OperationButton(onClick = { tappedEqualButton() }, content = { Text("=", fontSize = 24.sp) }) },
        { OperationButton(onClick = { tappedClearButton() }, content = { Text("C", fontSize = 24.sp) }) },
        { OperationButton(onClick = { tappedNumberButton(0F) }, content = { Text("0", fontSize = 24.sp) }) },
        { OperationButton(onClick = { tappedDecimalButton() }, content = { Text(".", fontSize = 24.sp) }) },
        { OperationButton(onClick = { tappedOperationButton(OperationType.BinaryOperationType.Modulo) }, content = { Text("%", fontSize = 24.sp) }) },
        { OperationButton(onClick = { tappedOperationButton(OperationType.BinaryOperationType.Division) }, content = { Text("/", fontSize = 24.sp) }) },
        { OperationButton(onClick = { tappedNumberButton(9f) }, content = { Text("9", fontSize = 24.sp) }) },
        { OperationButton(onClick = { tappedNumberButton(8f) }, content = { Text("8", fontSize = 24.sp) }) },
        { OperationButton(onClick = { tappedNumberButton(7f) }, content = { Text("7", fontSize = 24.sp) }) },
        { OperationButton(onClick = {  }, content = { Text("ln", fontSize = 24.sp) }) },
        { OperationButton(onClick = { tappedOperationButton(OperationType.BinaryOperationType.Multiplication) }, content = { Text("*", fontSize = 24.sp) }) },
        { OperationButton(onClick = { tappedNumberButton(6f) }, content = { Text("6", fontSize = 24.sp) }) },
        { OperationButton(onClick = { tappedNumberButton(5f) }, content = { Text("5", fontSize = 24.sp) }) },
        { OperationButton(onClick = { tappedNumberButton(4f) }, content = { Text("4", fontSize = 24.sp) }) },
        { OperationButton(onClick = { tappedOperationButton(OperationType.UnaryOperationType.Sqrt) }, content = { Text("√", fontSize = 24.sp) }) },
        { OperationButton(onClick = { tappedOperationButton(OperationType.BinaryOperationType.Subtraction) }, content = { Text("-", fontSize = 24.sp) }) },
        { OperationButton(onClick = { tappedNumberButton(3f) }, content = { Text("3", fontSize = 24.sp) }) },
        { OperationButton(onClick = { tappedNumberButton(2f) }, content = { Text("2", fontSize = 24.sp) }) },
        { OperationButton(onClick = { tappedNumberButton(1f) }, content = { Text("1", fontSize = 24.sp) }) },
        { OperationButton(onClick = { tappedOperationButton(OperationType.BinaryOperationType.Power) }, content = { Text("^", fontSize = 24.sp) }) },
        { OperationButton(onClick = { tappedOperationButton(OperationType.BinaryOperationType.Addition) }, content = { Text("+", fontSize = 24.sp) }) },
        { OperationButton(onClick = {  }, content = { Text(")", fontSize = 24.sp) }) },
        { OperationButton(onClick = {  }, content = { Text("(", fontSize = 24.sp) }) },
        { OperationButton(onClick = {  }, content = { Text("x^2", fontSize = 24.sp) }) },
        { OperationButton(onClick = {  }, content = { Text("log", fontSize = 24.sp) }) },
    )
}

private fun programmingGrid(
    tappedOperationButton: (OperationType) -> Unit,
    tappedClearButton: () -> Unit,
    tappedNumberButton: (Float) -> Unit,
    tappedEqualButton: () -> Unit,
): List<@Composable () -> Unit> {
    return listOf(
        { OperationButton(onClick = { tappedEqualButton() }, content = { Text("=", fontSize = 24.sp) }) },
        { OperationButton(onClick = { tappedClearButton() }, content = { Text("C", fontSize = 20.sp) }) },
        { OperationButton(onClick = { tappedNumberButton(0F) }, content = { Text("0", fontSize = 24.sp) }) },
        { OperationButton(onClick = { tappedNumberButton(1F) }, content = { Text("1", fontSize = 24.sp) }) },
        { OperationButton(onClick = { tappedOperationButton(OperationType.LogicOperationType.AND) }, content = { Text("AND", fontSize = 20.sp) }) },
        { OperationButton(onClick = { tappedOperationButton(OperationType.LogicOperationType.OR) }, content = { Text("OR", fontSize = 20.sp) }) },
        { OperationButton(onClick = { tappedOperationButton(OperationType.LogicOperationType.XOR) }, content = { Text("XOR", fontSize = 20.sp) }) },
        { OperationButton(onClick = { tappedOperationButton(OperationType.LogicOperationType.NOT) }, content = { Text("NOT", fontSize = 20.sp) }) },
        { OperationButton(onClick = { tappedOperationButton(OperationType.LogicOperationType.SHIFT_L) }, content = { Text("<<", fontSize = 20.sp) }) },
        { OperationButton(onClick = { tappedOperationButton(OperationType.LogicOperationType.SHIFT_R) }, content = { Text(">>", fontSize = 20.sp) }) },
        { OperationButton(onClick = { tappedOperationButton(OperationType.LogicOperationType.NAND) }, content = { Text("NAND", fontSize = 20.sp) }) },
        { OperationButton(onClick = { tappedOperationButton(OperationType.LogicOperationType.NOR) }, content = { Text("NOR", fontSize = 20.sp) }) },
    )
}

private fun scientificGrid(
    tappedOperationButton: (OperationType) -> Unit
): List<@Composable () -> Unit> {
    return listOf(
        { OperationButton(onClick = { tappedOperationButton(OperationType.UnaryOperationType.Sqrt) }, content = { Text("√", fontSize = 20.sp) }) },
        { OperationButton(onClick = { tappedOperationButton(OperationType.BinaryOperationType.Power) }, content = { Text("^", fontSize = 20.sp) }) },
        { OperationButton(onClick = { /* TODO: handle x² */ }, content = { Text("x²", fontSize = 20.sp) }) },
        { OperationButton(onClick = { /* TODO: handle ln */ }, content = { Text("ln", fontSize = 20.sp) }) },
        { OperationButton(onClick = { /* TODO: handle log */ }, content = { Text("log", fontSize = 20.sp) }) },
        { OperationButton(onClick = { /* TODO: handle parentheses */ }, content = { Text("(", fontSize = 20.sp) }) },
        { OperationButton(onClick = { /* TODO: handle parentheses */ }, content = { Text(")", fontSize = 20.sp) }) },
    )
}

@Preview(showBackground = true)
@Composable
fun MainScreenPreview() {
    val viewmodel = MainScreenViewmodel()
    AppTheme {
        MainScreen(viewmodel)
    }
}