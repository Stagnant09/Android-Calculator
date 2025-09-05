package com.example.calculator.ui.screens.main.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun MatrixInput(
    rows: Int,
    columns: Int,
    values: List<List<Float>>,
    onValueChange: (Int, Int, Float) -> Unit
) {
    Column(modifier = Modifier.fillMaxWidth()) { // outer = rows
        for (i in 0 until rows) {
            Row { // inner = columns
                for (j in 0 until columns) {
                    OutlinedTextField(
                        value = values[i][j].toString(),
                        onValueChange = {
                            val num = it.toFloatOrNull() ?: 0f
                            onValueChange(i, j, num)
                        },
                        singleLine = true,
                        modifier = Modifier
                            .width(64.dp)
                            .padding(2.dp),
                        textStyle = LocalTextStyle.current.copy(
                            fontSize = MaterialTheme.typography.bodyMedium.fontSize
                        ),
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun MatrixInputPreview() {
    MatrixInput(rows = 3, columns = 4, values = List(3) { List(4) { 0f } }, onValueChange = { _, _, _ -> })
}