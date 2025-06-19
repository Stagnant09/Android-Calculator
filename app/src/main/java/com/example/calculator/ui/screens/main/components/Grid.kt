package com.example.calculator.ui.screens.main.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import com.example.calculator.models.GridOrientation
import com.example.calculator.ui.theme.AppTheme

@Composable
fun Grid(
    rows: Int,
    columns: Int,
    content: List<@Composable () -> Unit>,
    modifier: Modifier = Modifier,
    orientation: GridOrientation = GridOrientation.UP_TO_DOWN_LEFT_TO_RIGHT
) {
    Column(
        modifier = modifier.fillMaxWidth()
    ) {
        for (row in 0 until rows) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center
            ) {
                for (column in 0 until columns) {
                    val index = getGridContentIndex(row, column, rows, columns, orientation)
                    Box(
                        modifier = Modifier.weight(1f),
                        contentAlignment = Alignment.Center
                    ) {
                        if (index in content.indices) {
                            content[index]()
                        } else {
                            Spacer(modifier = Modifier) // empty slot
                        }
                    }
                }
            }
        }
    }
}

private fun getGridContentIndex(row: Int, column: Int, rows: Int, columns: Int, orientation: GridOrientation): Int {
    return when (orientation) {
        GridOrientation.UP_TO_DOWN_LEFT_TO_RIGHT -> row * columns + column
        GridOrientation.UP_TO_DOWN_RIGHT_TO_LEFT -> row * columns + (columns - column - 1)
        GridOrientation.DOWN_TO_UP_LEFT_TO_RIGHT -> (rows - row - 1) * columns + column
        GridOrientation.DOWN_TO_UP_RIGHT_TO_LEFT -> (rows - row - 1) * columns + (columns - column - 1)
    }
}

@Preview
@Composable
fun GridPreview() {
    AppTheme {
        Surface(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight()
        ) {
            Grid(
                rows = 3,
                columns = 3,
                orientation = GridOrientation.UP_TO_DOWN_RIGHT_TO_LEFT,
                content = listOf(
                    { OperationButton(onClick = { }, content = { Text("1", fontSize = 24.sp) }) },
                    { OperationButton(onClick = { }, content = { Text("2", fontSize = 24.sp) }) },
                    { OperationButton(onClick = { }, content = { Text("3", fontSize = 24.sp) }) },
                    { OperationButton(onClick = { }, content = { Text("4", fontSize = 24.sp) }) },
                    { OperationButton(onClick = { }, content = { Text("5", fontSize = 24.sp) }) },
                    { OperationButton(onClick = { }, content = { Text("6", fontSize = 24.sp) }) },
                    { OperationButton(onClick = { }, content = { Text("7", fontSize = 24.sp) }) },
                    { OperationButton(onClick = { }, content = { Text("8", fontSize = 24.sp) }) },
                    { OperationButton(onClick = { }, content = { Text("9", fontSize = 24.sp) }) }
                )
            )
        }
    }
}